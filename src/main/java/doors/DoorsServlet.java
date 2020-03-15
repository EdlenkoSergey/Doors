package doors;

import doors.exceptions.ForbiddenEntranceException;
import doors.exceptions.InvalidEntranceException;
import doors.exceptions.InvalidRoomException;
import doors.exceptions.InvalidUserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "DoorServlet", urlPatterns = {"/check"})
public class DoorsServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger("doorServlet");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int roomId = getRoomId(req);
            boolean entrance = getEntrance(req);
            int keyId = getKeyId(req);
            HttpSession session = req.getSession();
            boolean entered = getEntered(session);
            enterRoom(roomId, entrance, keyId, session, entered);
        } catch (InvalidUserException | InvalidRoomException | InvalidEntranceException e) {
            log.error(e.getMessage());
            resp.setStatus(500);
        } catch (ForbiddenEntranceException e) {
            log.error(e.getMessage());
            resp.setStatus(403);
        }
    }

    public void enterRoom(int roomId, boolean entrance, int keyId, HttpSession session, boolean entered) {
        if (keyId % roomId != 0) {
            log.info("Вход запрещен");
            throw new ForbiddenEntranceException("Вход запрещен");
        }
        if (entered && entrance) {
            log.error("Пользователь уже зашел");
            throw new ForbiddenEntranceException("Вход запрещен");

        } else {
            if (entrance) {
                log.info("Пользователь зашел в комнату №{}", roomId);
            } else {
                log.info("Пользователь вышел из комнаты №{}", roomId);
            }
            session.setAttribute("entered", entrance);
        }
    }

    private boolean getEntered(HttpSession session) {
        try {
            return (boolean) session.getAttribute("entered");
        } catch (Exception e) {
            return false;
        }
    }

    private int getRoomId(HttpServletRequest request) {
        try {
            int roomId = Integer.parseInt(request.getParameter("roomId"));
            if (!(roomId > 0 && roomId < 5)) {
                throw new InvalidRoomException("Дверь с таким номером не существует");
            }
            return roomId;
        } catch (Exception e) {
            throw new InvalidRoomException("Дверь с таким номером не существует");
        }
    }

    private boolean getEntrance(HttpServletRequest request) {
        try {
            return request.getParameter("entrance").equals("true");
        } catch (Exception e) {
            throw new InvalidEntranceException("Атрибут Entrance не правильно передан");
        }
    }

    private int getKeyId(HttpServletRequest request) {
        try {
            int keyId = Integer.parseInt(request.getParameter("keyId"));
            if (!(keyId > 0 && keyId < 10000)) {
                throw new InvalidUserException("Пользователя с таким Id не существует");
            }
            return keyId;
        } catch (Exception e) {
            throw new InvalidUserException("Пользователя с таким Id не существует");
        }
    }

}
