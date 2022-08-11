package com.example.errormanager.api.repository;

import com.example.errormanager.api.domain.Developer;
import com.example.errormanager.api.dto.project.SendErrorDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Muhammadkomil Murodillayev, ср 11:39. 8/10/22
 */
@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Long>, BaseRepository {

    Optional<Developer> findByIdAndDeletedFalse(Long id);

    Optional<Developer> findByUsernameAndDeletedFalse(String username);

    Optional<Developer> findByChatIdAndDeletedFalse(String chatId);

//    @Query(value = "select d.chat_id " +
//            "from developer_project dp " +
//            "right join developer d on dp.developer_id = d.id " +
//            "where dp.project_id = ?1 ",
//            nativeQuery = true)
//    List<String> findAllChatId(Long projectId);

//    @Query(value = "select p.name, t.chat_id as chatId" +
//            "from (select d.chat_id, dp.project_id " +
//            "      from developer_project dp " +
//            "               right join developer d on dp.developer_id = d.id " +
//            "      where dp.project_id = :pid) t " +
//            "         left join project p on p.id = t.project_id;",
//            nativeQuery = true)
//    List<SendErrorDTO> findAllChatId(Long projectId);

}
