package org.springframework.social.vimeo.api;

import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.social.test.client.RequestMatchers.*;
import static org.springframework.social.test.client.ResponseCreators.withResponse;

/**
 * User: soldier
 * Date: 2/10/12
 * Time: 8:17 AM
 */
public class UploadTemplateTest extends AbstractVimeoApiTest {

    @Test
    public void testGetQuota() {
        mockServer.expect(requestTo("https://vimeo.com/api/rest/v2"))
                .andExpect(method(GET))
                .andExpect(headerContains("Authorization", "OAuth oauth_version"))
                .andRespond(withResponse(jsonResource("upload_quota"), responseHeaders));

        Quota quota = vimeo.uploadOperations().quota();
        assertEquals("101193", quota.getId());
        assertEquals(Boolean.TRUE, quota.getHdQuota());
        assertEquals(Boolean.TRUE, quota.getSdQuota());
        assertEquals(Boolean.TRUE, quota.getPlus());
        assertEquals(Long.valueOf(1073741824l), quota.getUploadSpace().getFree());
        assertEquals(Long.valueOf(5368709120l), quota.getUploadSpace().getMax());
        assertEquals(Long.valueOf(3l), quota.getUploadSpace().getResets());
        assertEquals(Long.valueOf(0l), quota.getUploadSpace().getUsed());
    }

    @Test
    public void testGetTicket() throws MalformedURLException {
        mockServer.expect(requestTo("https://vimeo.com/api/rest/v2"))
                .andExpect(method(GET))
                .andExpect(headerContains("Authorization", "OAuth oauth_version"))
                .andRespond(withResponse(jsonResource("upload_ticket"), responseHeaders));

        Ticket ticket = vimeo.uploadOperations().ticket(null, null);
        assertEquals("abcdef124567890", ticket.getId());
        assertEquals("1.2.3.4", ticket.getHost());
        assertEquals(new URL("http://1.2.3.4/upload?ticket_id=abcdef124567890"), ticket.getEndpoint());
        assertEquals(Long.valueOf(524288000l), ticket.getMaxFileSize());
    }
}
