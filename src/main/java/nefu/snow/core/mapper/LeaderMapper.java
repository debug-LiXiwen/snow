package nefu.snow.core.mapper;

import nefu.snow.core.mapper.provider.LeaderProvider;
import nefu.snow.core.model.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by LiXiwen on 2019/5/13 9:21.
 **/
@Repository
@Mapper
public interface LeaderMapper {


    @Select("select user_score from user where user_id=#{userId}")
    double selectUserScore(User user);
    @Select("select score from team where team_id=#{userTeamId}")
    double selectTeamScore(User user);

    @Select("SELECT team_task_content AS teamTaskContent,team_task_publishtime AS teamTaskPublishTime from team WHERE team_id=#{teamId}")
    Team selectTeamTaskByTeamId(String teamId);

    @Update("UPDATE user SET num=#{num} WHERE user_id=#{userId}")
    int updateProgress(@Param("userId") String userId,@Param("num") int num);

    @SelectProvider(type = LeaderProvider.class, method = "selectSystemByCondition")
    List<User> selectSystem(User user);

    @Select("SELECT count(system_id) from user where user_teamid=#{userTeamId}")
    int countNum(User user);

    @Select("SELECT SUM(num) from user where user_teamid=#{userTeamId}")
    int selectTotalNum(User user);

    @Update("UPDATE user SET user_score=#{userScore} WHERE user_id=#{userId}")
    int updateScore(User user);

    @Update("UPDATE user set user_task=#{userTask},user_tasktime=#{userTaskTime} WHERE user_id=#{userId}")
    int updateUserTask(User user);

    @Update("UPDATE tool SET state=#{state} WHERE tool_id=#{toolId}")
    int updateToolState(Tool tool);

    @Update("UPDATE car SET state=#{state} WHERE car_id=#{carId}")
    int updateCarState(Car car);

    @Select("SELECT car_id AS carId,num,state,teamId from car")
    List<Car> selectCars();

    @Select("SELECT tool_id AS toolId,tool_name AS toolName , state ,teamId from tool where teamId=#{teamId}")
    List<Tool> selectToolsTeam(Tool tool);

    @Select("SELECT car_id AS carId,num,state,teamId from car where teamId=#{teamId}")
    List<Car> selectCarsTeam(Car car);

    @Update("update team set progress=#{progress} where team_id=#{teamId}")
    int updateProgressTeam(Team team);

    @Select("SELECT tool_id AS toolId,tool_name AS toolName , state ,teamId from tool")
    List<Tool> selectTools();

    //@SelectProvider(type = LeaderProvider.class , method = "selectUserList")
    @Select("select system_id AS systemId,user_id AS userId,user_name AS userName, user_password AS userPassword,user_token AS userToken," +
            "user_type AS userType,user_votenum AS userVoteNum,user_vstate AS userVstate ,user_cstate AS cstate,user_score AS userScore," +
            "user_workage AS userWorkAge,user_address AS userAddress,user_reward AS userReward,user_sex AS userSex,user_worktime as userWorkTime," +
            "user_starttime AS userStartTime,user_endtime AS userEndTime,user_tasktime AS userTaskTime,num,astate,user_teamid AS userTeamId ,idcard,user_task AS userTask from user where user_type!='B' and user_type!='C'")
    List<User> selectStarfs();

    @Select("SELECT team_id AS teamId,team_name AS teamName ,team_task_content AS teamTaskContent,score,astate,applyContent,progress from team")
    List<Team> selectTeams();

    @Select("SELECT teamId,teamName,astate ,applyContent from needs")
    List<Needs> selectApplications();

    @Select("select team_name from team where team_id=#{teamId}")
    String selectTeamName(String teamId);

    @Insert("INSERT INTO car(num,state) VALUES(#{num},#{state})")
    int insertCar(Car car);

    @Insert("INSERT INTO tool(tool_name,state) VALUES(#{toolName},#{state})")
    int insertTool(Tool tool);

    @Update("UPDATE user SET user_teamid=#{userTeamId} WHERE user_id=#{userId}")
    int changeTeamUser(User user);

    @Update("UPDATE car SET teamId=#{teamId} WHERE car_id=#{carId}")
    int changeCars(Car car);

    @Update("UPDATE tool SET teamId=#{teamId} WHERE tool_id=#{toolId}")
    int changeTools(Tool tool);

    @Update("UPDATE team SET team_task_content=#{teamTaskContent} WHERE team_id=#{teamId}")
    int changeTeamTasks(Team team);

    @Update("UPDATE team SET astate=#{astate} WHERE team_id=#{teamId}")
    int solveTeamApply(Team team);

    @Delete("DELETE from team WHERE team_id=#{teamId}")
    int deleteTeamApplyById(Team team);

    @Update("UPDATE performsystem SET state=#{state}")
    int updateSysState(Integer state);

    @Update("UPDATE system SET system_state=#{state}")
    int updateVoteState(Integer state);


    @Select("SELECT state from performsystem")
    int performState();

    @Update("UPDATE team SET score=#{score} WHERE team_id=#{teamId}")
    int updateTeamScore(Team team);

    @Update("UPDATE needs SET applyContent=#{applyContent} WHERE teamId=#{teamId}")
    int updateTeamApplyContent(Needs needs);

    @Select("SELECT teamId,applyContent from needs WHERE teamId=#{teamId}")
    Needs selectTeamApplyContent(Needs needs);

    @Insert("insert into team(team_id,team_password,team_name,score,progress) values(#{teamId},#{teamPassword},#{teamName},#{score},#{progress})")
    int addTeam(Team team);

    @Insert("insert into user(user_id,user_password,user_type,user_name) values(#{teamId},#{teamPassword},'B',#{teamName})")
    int addTeamUser(Team team);
    @Insert("insert into needs(teamId,teamName,applyContent,astate) values(#{teamId},#{teamName},#{applyContent},'0')")
    int addTeamNeeds(Team team);




}
