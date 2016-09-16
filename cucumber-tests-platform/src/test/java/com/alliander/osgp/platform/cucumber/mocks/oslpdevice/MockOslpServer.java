package com.alliander.osgp.platform.cucumber.mocks.oslpdevice;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;

import org.junit.Assert;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.osgp.adapter.protocol.dlms.infra.messaging.DeviceRequestMessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alliander.osgp.oslp.Oslp;
import com.alliander.osgp.oslp.Oslp.GetFirmwareVersionResponse;
import com.alliander.osgp.oslp.Oslp.Message;
import com.alliander.osgp.oslp.Oslp.SetLightResponse;
import com.alliander.osgp.oslp.Oslp.Status;
import com.alliander.osgp.oslp.OslpDecoder;
import com.alliander.osgp.oslp.OslpEncoder;
import com.alliander.osgp.shared.security.CertificateHelper;

@Component
public class MockOslpServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockOslpServer.class);

    @Value("${oslp.port.server}")
    private int oslpPortServer;

    @Value("${oslp.security.signature}")
    private String oslpSignature;

    @Value("${oslp.security.provider}")
    private String oslpSignatureProvider;

    @Value("${oslp.timeout.connect}")
    private int connectionTimeout;

    @Value("${oslp.security.signkey.path}")
    private String signKeyPath;

    @Value("${oslp.security.verifykey.path}")
    private String verifyKeyPath;

    @Value("${oslp.security.keytype}")
    private String keytype;

    @Value("${oslp.sequence.number.window}")
    private Integer sequenceNumberWindow;

    @Value("${oslp.sequence.number.maximum}")
    private Integer sequenceNumberMaximum;

    @Value("${response.delay.time}")
    private Long responseDelayTime;

    @Value("${response.delay.random.range}")
    private Long reponseDelayRandomRange;

    private ServerBootstrap server;

    // TODO split channel handler in client/server
    private ChannelHandler channelHandler;

    private final ConcurrentMap<DeviceRequestMessageType, Message> mockResponses = new ConcurrentHashMap<>();
    private final ConcurrentMap<DeviceRequestMessageType, Message> receivedRequests = new ConcurrentHashMap<>();

    public void start() throws Throwable {
        this.channelHandler = new MockOslpChannelHandler(this.oslpSignature, this.oslpSignatureProvider,
                this.connectionTimeout, this.sequenceNumberWindow, this.sequenceNumberMaximum, this.responseDelayTime,
                this.reponseDelayRandomRange, this.privateKey(), this.clientBootstrap(), this.mockResponses,
                this.receivedRequests);

        this.server = this.serverBootstrap();
        this.server.bind(new InetSocketAddress(this.oslpPortServer));
        LOGGER.info("Started OSLP Mock server on port {}", this.oslpPortServer);
    }

    public void stop() {
        this.server.shutdown();
        LOGGER.info("Shutdown OSLP Mock server");
    }
    
    public void resetServer() {
        this.mockResponses.clear();
    }

    public Message waitForRequest(final DeviceRequestMessageType requestType) {
        int count = 0;
        while (!this.receivedRequests.containsKey(requestType)) {
            try {
                count++;
                LOGGER.info("Sleeping 1s " + count);
                Thread.sleep(1000);
            } catch (final InterruptedException e) {
                Assert.fail("Polling for response interrupted");
            }

            if (count > 60) {
                Assert.fail("Polling for response failed, no reponse found");
            }
        }

        return this.receivedRequests.get(requestType);
    }

    private ServerBootstrap serverBootstrap() {
        final ChannelFactory factory = new NioServerSocketChannelFactory(Executors.newCachedThreadPool(),
                Executors.newCachedThreadPool());

        final ServerBootstrap bootstrap = new ServerBootstrap(factory);

        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline()
                    throws InvalidKeySpecException, NoSuchAlgorithmException, IOException, NoSuchProviderException {
                final ChannelPipeline pipeline = MockOslpServer.this.createPipeLine();
                LOGGER.info("Created new server pipeline");
                return pipeline;
            }
        });

        bootstrap.setOption("child.tcpNoDelay", true);
        bootstrap.setOption("child.keepAlive", false);

        return bootstrap;
    }

    private ClientBootstrap clientBootstrap() {
        final ChannelFactory factory = new NioClientSocketChannelFactory(Executors.newCachedThreadPool(),
                Executors.newCachedThreadPool());

        final ChannelPipelineFactory pipelineFactory = new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline()
                    throws InvalidKeySpecException, NoSuchAlgorithmException, IOException, NoSuchProviderException {
                final ChannelPipeline pipeline = MockOslpServer.this.createPipeLine();
                LOGGER.info("Created new client pipeline");
                return pipeline;
            }
        };

        final ClientBootstrap bootstrap = new ClientBootstrap(factory);

        bootstrap.setOption("tcpNoDelay", true);
        bootstrap.setOption("keepAlive", false);
        bootstrap.setOption("connectTimeoutMillis", this.connectionTimeout);

        bootstrap.setPipelineFactory(pipelineFactory);

        return bootstrap;
    }

    private ChannelPipeline createPipeLine()
            throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException, IOException {
        final ChannelPipeline pipeline = Channels.pipeline();

        pipeline.addLast("oslpEncoder", new OslpEncoder());
        pipeline.addLast("oslpDecoder", new OslpDecoder(this.oslpSignature, this.oslpSignatureProvider));
        pipeline.addLast("oslpSecurity", new OslpSecurityHandler(this.publicKey()));
        pipeline.addLast("oslpChannelHandler", this.channelHandler);
        return pipeline;
    }

    private PublicKey publicKey()
            throws NoSuchAlgorithmException, IOException, InvalidKeySpecException, NoSuchProviderException {
        return CertificateHelper.createPublicKey(this.verifyKeyPath, this.keytype, this.oslpSignatureProvider);
    }

    private PrivateKey privateKey()
            throws IOException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchProviderException {
        return CertificateHelper.createPrivateKey(this.signKeyPath, this.keytype, this.oslpSignatureProvider);
    }

    public void mockFirmwareResponse(final String fwVersion) {
        this.mockResponses.put(DeviceRequestMessageType.GET_FIRMWARE_VERSION, Oslp.Message.newBuilder()
                .setGetFirmwareVersionResponse(GetFirmwareVersionResponse.newBuilder().setFirmwareVersion(fwVersion))
                .build());
    }

	public void mockSetLightResponse(Oslp.Status status) {
		this.mockResponses.put(DeviceRequestMessageType.SET_LIGHT, Oslp.Message.newBuilder()
				.setSetLightResponse(SetLightResponse.newBuilder().setStatus(status))
				.build());
	}
}
