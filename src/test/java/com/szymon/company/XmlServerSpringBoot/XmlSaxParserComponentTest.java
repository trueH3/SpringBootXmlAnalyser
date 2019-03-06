package com.szymon.company.XmlServerSpringBoot;

import java.time.LocalDateTime;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockserver.integration.ClientAndServer;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.verify.VerificationTimes;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author szymon
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class XmlSaxParserComponentTest {

    private ClientAndServer mockServer;
    
    @Autowired
    private XmlSaxParser parserService;

    @Before
    public void startMockServer() {
        mockServer = startClientAndServer(1081);
    }

    @After
    public void stopMockServer() {
        mockServer.stop();
    }

    @Test
    public void verifyRequestAndCheckReturningValues() {

        //Given
        mockServer.when(HttpRequest
                .request()
                .withMethod("GET")
                .withPath("/pathToSomeXml"))
                .respond(HttpResponse.response()
                        .withStatusCode(200)
                        .withBody("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
                                + "<posts>\n"
                                + "  <row Id=\"2\" PostTypeId=\"1\" CreationDate=\"2017-08-05T04:47:45.557\" Score=\"7\" ViewCount=\"51\" Body=\"&lt;p&gt;So far most Augur SE answers seem to be of high quality. However a large percentage of them have been answered by &lt;a href=&quot;https://augur.stackexchange.com/users/13/micah-zoltu&quot;&gt;one person&lt;/a&gt;. This speaks highly of him, but not of the effort of the rest of the community with the technical ability to answer hard questions.&lt;/p&gt;&#xA;&#xA;&lt;p&gt;How can we go about encouraging more experts to answer difficult questions, and remind more users that it is &lt;a href=&quot;https://stackoverflow.com/help/self-answer&quot;&gt;okay to ask and answer their own questions&lt;/a&gt; which they are capable of answering?&lt;/p&gt;&#xA;\" OwnerUserId=\"138\" LastActivityDate=\"2017-08-05T22:28:16.757\" Title=\"Encouraging more experts to answer questions\" Tags=\"&lt;discussion&gt;\" AnswerCount=\"1\" CommentCount=\"1\" FavoriteCount=\"3\" />\n"
                                + "  <row Id=\"6\" PostTypeId=\"2\" AcceptedAnswerId=\"5\" ParentId=\"2\" CreationDate=\"2017-08-05T22:28:16.757\" Score=\"0\" Body=\"&lt;p&gt;I think your suggestion of reminding people that self answers are welcome is a good idea. In addition I suggest the following:&lt;/p&gt;&#xA;&#xA;&lt;ol&gt;&#xA;&lt;li&gt;&lt;p&gt;If an answer already exists, don't be deterred. Multiple answers are encouraged with the most helpful answers generally being voted to the top over time.&lt;/p&gt;&lt;/li&gt;&#xA;&lt;li&gt;&lt;p&gt;People answering questions should not be in a rush to accept the first answer that (partially) answers the question. Waiting a little longer to accept an answer as correct may encourage edits to improve the initial answer or additional answers that better answer your question.&lt;/p&gt;&lt;/li&gt;&#xA;&lt;li&gt;&lt;p&gt;Look at the &lt;a href=&quot;https://augur.stackexchange.com/unanswered&quot;&gt;unanswered questions&lt;/a&gt; tab in order to find questions that have not been answered. Nobody is aware 24 hours a day. If there is consistently someone that provides a very high quality answer before you can draft your own, try logging on during a different time of day.&lt;/p&gt;&lt;/li&gt;&#xA;&lt;li&gt;&lt;p&gt;Invite more people to participate in this Stack and explain to them why having a high quality, searchable, expert Q&amp;amp;A site for Augur is beneficial.&lt;/p&gt;&lt;/li&gt;&#xA;&lt;/ol&gt;&#xA;\" OwnerUserId=\"114\" LastActivityDate=\"2017-08-05T22:28:16.757\" CommentCount=\"1\" />\n"
                                + "  <row Id=\"9\" PostTypeId=\"2\" AcceptedAnswerId=\"1\" ParentId=\"8\" CreationDate=\"2017-08-07T07:22:20.770\" Score=\"0\" Body=\"&lt;p&gt;I think the context provided by the example historical question you provided would be a useful addition to this Stack.  In order to understand the reasons for the Augur design, it is helpful to understand why and how changes to the design were made.&lt;/p&gt;&#xA;\" OwnerUserId=\"193\" LastActivityDate=\"2017-08-07T07:22:20.770\" CommentCount=\"0\" />\n"
                                + "</posts>")
                );
        //When
        String string = "http://127.0.0.1:1081/pathToSomeXml";
        Result ResultTocheck = parserService.parseXml(string);
        //Then
        mockServer.verify(HttpRequest.request().withPath("/pathToSomeXml"));
        mockServer.verify(HttpRequest.request("/pathToSomeXml"), VerificationTimes.once());
        assertEquals(3L, ResultTocheck.getTotalPosts());
        assertEquals(LocalDateTime.of(2017, 8, 5, 4, 47, 45, 557000000), ResultTocheck.getFirstPost());
        assertEquals(LocalDateTime.of(2017, 8, 7, 7, 22, 20, 770000000), ResultTocheck.getLastPost());
        assertEquals(2L, ResultTocheck.getTotalAcceptedPosts());
        assertEquals(2, ResultTocheck.getAvgScore());
    }
}