package doors;

import doors.exceptions.ForbiddenEntranceException;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class DoorsTest {
    @Test
    public void shouldReturn500IfTheUserDoesNotExist() throws ServletException, IOException {
        DoorsServlet doorsServlet = new DoorsServlet();
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        doReturn("10001").when(httpServletRequest).getParameter("keyId");
        doReturn("1").when(httpServletRequest).getParameter("roomId");
        doReturn("true").when(httpServletRequest).getParameter("entrance");
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
        doorsServlet.doGet(httpServletRequest, httpServletResponse);
        verify(httpServletResponse).setStatus(500);
    }

    @Test
    public void shouldReturn500IfTheUserNull() throws ServletException, IOException {
        DoorsServlet doorsServlet = new DoorsServlet();
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        doReturn(null).when(httpServletRequest).getParameter("keyId");
        doReturn("1").when(httpServletRequest).getParameter("roomId");
        doReturn("true").when(httpServletRequest).getParameter("entrance");
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
        doorsServlet.doGet(httpServletRequest, httpServletResponse);
        verify(httpServletResponse).setStatus(500);
    }

    @Test
    public void shouldReturn500IfTheRoomDoesNotExist() throws ServletException, IOException {
        DoorsServlet doorsServlet = new DoorsServlet();
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        doReturn("1").when(httpServletRequest).getParameter("keyId");
        doReturn("6").when(httpServletRequest).getParameter("roomId");
        doReturn("true").when(httpServletRequest).getParameter("entrance");
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
        doorsServlet.doGet(httpServletRequest, httpServletResponse);
        verify(httpServletResponse).setStatus(500);
    }

    @Test
    public void shouldReturn500IfTheRoomNull() throws ServletException, IOException {
        DoorsServlet doorsServlet = new DoorsServlet();
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        doReturn("1").when(httpServletRequest).getParameter("keyId");
        doReturn(null).when(httpServletRequest).getParameter("roomId");
        doReturn("true").when(httpServletRequest).getParameter("entrance");
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
        doorsServlet.doGet(httpServletRequest, httpServletResponse);
        verify(httpServletResponse).setStatus(500);
    }

    @Test
    public void shouldReturn500IfTheInvalidEntrance() throws ServletException, IOException {
        DoorsServlet doorsServlet = new DoorsServlet();
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        doReturn("1").when(httpServletRequest).getParameter("keyId");
        doReturn("1").when(httpServletRequest).getParameter("roomId");
        doReturn(null).when(httpServletRequest).getParameter("entrance");
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
        doorsServlet.doGet(httpServletRequest, httpServletResponse);
        verify(httpServletResponse).setStatus(500);
    }

    @Test(expected = ForbiddenEntranceException.class)
    public void shouldThrowExceptionIfKeyIdIsNotDivisibleByRoomId() {
        DoorsServlet doorsServlet = new DoorsServlet();
        doorsServlet.enterRoom(3, true, 2, null, false);
    }

    @Test(expected = ForbiddenEntranceException.class)
    public void shouldThrowExceptionIfAlreadyEntred() {
        DoorsServlet doorsServlet = new DoorsServlet();
        doorsServlet.enterRoom(1, true, 2, null, true);
    }
}
