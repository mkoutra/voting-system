package personal.service;

import personal.dao.exceptions.UserDAOException;
import personal.dto.UserInsertDTO;

public interface IUserService {
    void insertUser(UserInsertDTO dto) throws UserDAOException;
}
