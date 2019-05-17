package nefu.snow.service.impl;

import nefu.snow.core.mapper.LeaderMapper;
import nefu.snow.core.model.*;
import nefu.snow.service.LeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by LiXiwen on 2019/5/13 9:22.
 **/
@Service
public class LeaderServiceImpl implements LeaderService {

    private final LeaderMapper leaderMapper;

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    public LeaderServiceImpl(LeaderMapper leaderMapper){
        this.leaderMapper = leaderMapper;
    }


    @Override
    public Map<String,Object> selectTeamTaskByTeamId(String teamId) {
        Team team  = leaderMapper.selectTeamTaskByTeamId(teamId);
        Map<String,Object> rtv = new LinkedHashMap<>();
        Map<String,Object> data = new LinkedHashMap<>();

        if(null != team){
            data.put("content",team.getTeamTaskContent());
            data.put("time",format.format(team.getTeamTaskPublishTime()));
            rtv.put("code",0);
            rtv.put("data",data);
        }else{
            rtv.put("code",1);
            rtv.put("message","小组不存在");
        }
        return rtv;
    }

    @Override
    public Map<String, Object> updateTeamApplyContent(Needs needs) {
        Map<String,Object> rtv = new LinkedHashMap<>();
        Map<String,Object> data = new LinkedHashMap<>();
        Needs needs1 = new Needs();
        needs.setTeamName(leaderMapper.selectTeamName(needs.getTeamId()));
        if(0 < leaderMapper.updateTeamApplyContent(needs)){
            needs1 = leaderMapper.selectTeamApplyContent(needs);
            data.put("teamId",needs1.getTeamId());
            data.put("content",needs1.getApplyContent());
            rtv.put("code",0);
            rtv.put("data",data);
        }else{
            rtv.put("code",1);
            rtv.put("message","失败");
        }

        return rtv;
    }

    @Override
    public Map<String, Object> updateProgress(String userId, int num) {
        Map<String,Object> rtv = new LinkedHashMap<>();
        int a = leaderMapper.updateProgress(userId, num);
        if(0 >= a){
            rtv.put("code",1);
            rtv.put("message","更新失败");
        }else{
            rtv.put("code",0);
        }
        return rtv;
    }

    @Override
    public Map<String, Object> selectSystem(String teamId) {
        Map<String,Object> rtv = new LinkedHashMap<>();
        User user = new User();
        user.setUserTeamId(teamId);
        List<User> users = leaderMapper.selectSystem(user);

        int totalNum = leaderMapper.selectTotalNum(user);


        rtv.put("data",users);    int people = leaderMapper.countNum(user);
        double a = totalNum/people;

        Team team =new Team();
        team.setTeamId(teamId);
        team.setProgress(a);
        int f = leaderMapper.updateProgressTeam(team);
        if(0<f){
            rtv.put("code",0);
            rtv.put("averageProgress",a);
        }else{
            rtv.put("code",0);
            rtv.put("message","错误");
        }

        return rtv;
    }

    @Override
    public Map<String, Object> updateScore(User user) {
        Map<String,Object> rtv = new LinkedHashMap<>();
        //double userScore = leaderMapper.selectUserScore(user);
        double teamScore = leaderMapper.selectTeamScore(user);
        user.setUserScore(0.5*teamScore+user.getUserScore());
        int a = leaderMapper.updateScore(user);
        if(0 >= a){
            rtv.put("code",1);
            rtv.put("message","更新失败");
        }else{
            rtv.put("code",0);

        }
        return rtv;
    }

    @Override
    public Map<String, Object> updateUserTask(User user) {
        Map<String,Object> rtv = new HashMap<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time = null;
        try {
            time= sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        user.setUserTaskTime(time);
        int a = leaderMapper.updateUserTask(user);
        if(0 < a){
            rtv.put("code",0);
        } else{
            rtv.put("code",1);
            rtv.put("message","更新失败");
        }
        return rtv;
    }

    @Override
    public Map<String, Object> updateToolState(Tool tool) {
        Map<String, Object> rtv  =new HashMap<>();
        if(0 != leaderMapper.updateToolState(tool)){
            rtv.put("code",0);
        }else{
            rtv.put("code",1);
            rtv.put("message","更新失败");
        }
        return rtv;
    }

    @Override
    public Map<String, Object> updateCarState(Car car) {
        Map<String, Object> rtv  =new HashMap<>();
        if(0 != leaderMapper.updateCarState(car)){
            rtv.put("code",0);
        }else{
            rtv.put("code",1);

        }
        return rtv;
    }

    @Override
    public Map<String, Object> selectCarsTeam(Car car) {

        Map<String, Object> rtv  =new LinkedHashMap<>();
        List<Car> cars = leaderMapper.selectCarsTeam(car);
        if(null != cars){
            rtv.put("code",0);
            rtv.put("data",cars);
        }else{
            rtv.put("code",1);
            rtv.put("message","error");
        }

        return rtv;
    }

    @Override
    public Map<String, Object> selectToolsTeam(Tool tool) {
        Map<String, Object> rtv  =new LinkedHashMap<>();
        List<Tool> tools = leaderMapper.selectToolsTeam(tool);
        if(null != tools){
            rtv.put("code",0);
            rtv.put("data",tools);
        }else{
            rtv.put("code",1);
            rtv.put("message","error");
        }

        return rtv;
    }

    @Override
    public Map<String, Object> selectCars() {
        Map<String, Object> rtv  =new LinkedHashMap<>();
        List<Car> cars = leaderMapper.selectCars();
        if(null != cars){
            rtv.put("code",0);
            rtv.put("data",cars);
        }else{
            rtv.put("code",1);
            rtv.put("message","error");
        }

        return rtv;
    }

    @Override
    public Map<String, Object> selectTools() {
        Map<String, Object> rtv  =new LinkedHashMap<>();
        List<Tool> tools = leaderMapper.selectTools();
        if(null != tools){
            rtv.put("code",0);
            rtv.put("data",tools);
        }else{
            rtv.put("code",1);
            rtv.put("message","error");
        }

        return rtv;
    }

    @Override
    public Map<String, Object> selectStarfs() {
        Map<String, Object> rtv  =new LinkedHashMap<>();
        List<User> users = leaderMapper.selectStarfs();
        if(null != users){
            rtv.put("code",0);
            rtv.put("data",users);
        } else {
            rtv.put("code",1);
            rtv.put("message","error");
        }
        return rtv;
    }

    @Override
    public Map<String, Object> selectTeams() {
        Map<String, Object> rtv  =new LinkedHashMap<>();
        List<Team> teams = leaderMapper.selectTeams();
        if(null != teams){
            rtv.put("code",0);
            rtv.put("data",teams);
        } else {
            rtv.put("code",1);
            rtv.put("message","error");
        }
        return rtv;
    }

    @Override
    public Map<String, Object> selectApplications() {
        Map<String, Object> rtv  =new LinkedHashMap<>();
        List<Needs> applications = leaderMapper.selectApplications();
        System.out.println(applications);
        List<Map<String, Object>> list = new ArrayList<>();
        if(null != applications){
            for(Needs needs : applications){
                Map<String, Object> data  = new LinkedHashMap<>();
                data.put("teamId",needs.getTeamId());
                data.put("teamName",needs.getTeamName());
                data.put("state",needs.getAstate()) ;
                data.put("content",needs.getApplyContent());
                list.add(data);
            }
            rtv.put("code",0);
            rtv.put("data",list);
        }else{
            rtv.put("code",1);
            rtv.put("message","error!");
        }
        return rtv;
    }

    @Override
    public Map<String, Object> insertCar(Car car) {
        Map<String,Object> rtv = new LinkedHashMap<>();
        int a = leaderMapper.insertCar(car);
        if(0 != a){
            rtv.put("code",0);
        }else{
            rtv.put("code",1);
        }
        return rtv;
    }

    @Override
    public Map<String, Object> insertTool(Tool tool) {
        Map<String,Object> rtv = new LinkedHashMap<>();
        int a = leaderMapper.insertTool(tool);
        if(0 != a){
            rtv.put("code",0);
        }else{
            rtv.put("code",1);
        }
        return rtv;
    }

    @Override
    public Map<String, Object> changeTeamUser(User user) {
        Map<String,Object> rtv = new LinkedHashMap<>();
        if(0 != leaderMapper.changeTeamUser(user)){
            rtv.put("code",0);
        }else{
            rtv.put("code",1);
        }
        return rtv;
    }

    @Override
    public Map<String, Object> changeTool(Tool tool) {
        Map<String,Object> rtv = new HashMap<>();
        if(0 != leaderMapper.changeTools(tool)){
            rtv.put("code",0);
        }else{
            rtv.put("code",1);
        }
        return rtv;
    }

    @Override
    public Map<String, Object> changeCar(Car car) {
        Map<String,Object> rtv = new HashMap<>();
        if(0 != leaderMapper.changeCars(car)){
            rtv.put("code",0);
        }else{
            rtv.put("code",1);
        }
        return rtv;
    }

    @Override
    public Map<String, Object> changeTeamTask(Team team) {
        Map<String,Object> rtv = new HashMap<>();
        if(0 != leaderMapper.changeTeamTasks(team)){
            rtv.put("code",0);
        }else{
            rtv.put("code",1);
        }
        return rtv;
    }

    @Override
    public Map<String, Object> putSysState(Integer state) {
        Map<String,Object> rtv = new HashMap<>();
        if( 0 != leaderMapper.updateSysState(state)){
            rtv.put("code",0);
        }else{
            rtv.put("code",1);
        }
        return rtv;
    }

    @Override
    public Map<String, Object> putVoteState(Integer state) {
        Map<String,Object> rtv = new HashMap<>();
        if( 0 != leaderMapper.updateVoteState(state)){
            rtv.put("code",0);
        }else{
            rtv.put("code",1);
        }
        return rtv;
    }

    @Override
    public Map<String, Object> solveApply(Team team) {
        Map<String,Object> rtv = new HashMap<>();
        if(-1 == team.getAstate()){

            int a =leaderMapper.deleteTeamApplyById(team);

            rtv.put("code",0);
        }else{
            if(0 != leaderMapper.solveTeamApply(team)){
                rtv.put("code",0);
            }else{
                rtv.put("code",1);
            }
        }

        return rtv;
    }

    @Override
    public Map<String, Object> performState() {
        Map<String,Object> rtv = new HashMap<>();
        int a = leaderMapper.performState();
        if(0 == a){
            rtv.put("state",0);
        }else if(1 == a){
            rtv.put("state",1);
        }else{
            rtv.put("code","error");
        }
        return rtv;
    }

    @Override
    public Map<String, Object> updateTeamScore(Team team) {
        Map<String,Object> rtv = new HashMap<>();
        if(0 < leaderMapper.updateTeamScore(team)){
            rtv.put("code",0);
        }else{
            rtv.put("code",1);
        }
        return rtv;
    }


    @Override
    public Map<String, Object> addTeam(Team team) {
        Map<String,Object> rtv = new LinkedHashMap<>();
        team.setProgress(0.0);
        team.setScore(0);
        team.setTeamName("team"+team.getTeamId());
        int a1 = leaderMapper.addTeam(team);
        int a2 = leaderMapper.addTeamUser(team);
        int a3 = leaderMapper.addTeamNeeds(team);
        if(0 < a1 && 0 < a2 && 0 < a3){
            rtv.put("code",0);
        }else{
            rtv.put("code",1);
            rtv.put("message","添加小组失败");
        }
        return rtv;
    }
}
