package com.sirius.tree;

import com.sirius.tree.domain.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App2 {

    private static List<UserInfoCathe2> userInfoList = new ArrayList<>();
    public static List<UserTypeCathe> userTypeList = new ArrayList<>();
    public static List<UserCodeCathe> userCodeList = new ArrayList<>();

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private static Random random = new Random();

    public static void main(String[] args) throws ParseException {
        System.out.println("开始生成");
        LocalDateTime localDate1 = LocalDateTime.now();
        for (int i = 0; i < 200000; i++) {
            userInfoList.add(new UserInfoCathe2(i));
            userInfoList.add(new UserInfoCathe2(i));
            userInfoList.add(new UserInfoCathe2(i));
            int typeCount = random.nextInt(10);
            for (int j = 0; j < typeCount; j++) {
                userTypeList.add(new UserTypeCathe(i));
            }
            int codeCount = random.nextInt(20);
            for (int j = 0; j < codeCount; j++) {
                userCodeList.add(new UserCodeCathe(i));
            }
        }
        Map<Integer, List<UserInfoCathe2>> map = userInfoList.parallelStream().collect(Collectors.groupingBy(UserInfoCathe2::getId));
        for (UserTypeCathe userTypeCathe : userTypeList) {
            List<UserInfoCathe2> map2 = map.get(userTypeCathe.getId());
            for (UserInfoCathe2 userInfoCathe2 : map2) {
                userInfoCathe2.getUserTypeCathe().add(userTypeCathe);
            }
        }
        for (UserCodeCathe userCodeCathe : userCodeList) {
            List<UserInfoCathe2> map2 = map.get(userCodeCathe.getId());
            for (UserInfoCathe2 userInfoCathe2 : map2) {
                userInfoCathe2.getUserCodeCathe().add(userCodeCathe);
            }
        }
        // 查询条件
        Search search = new Search();
        search.setStartDate(sdf.parse("2019-05-05"));
        search.setEndDate(sdf.parse("2019-06-23"));
        search.setSex("2");
        for (int i = 1; i < 10; i++) {
            int num = i * 10;
            search.getType().add("1" + num);
        }
        for (int i = 2; i < 20; i++) {
            int num = i * 5;
            search.getCode().add("2" + num);
        }
        // 开始查询
        LocalDateTime localDate2 = LocalDateTime.now();
        System.out.println("生成结束 耗时" + localDate1.until(localDate2, ChronoUnit.MILLIS) + "毫秒\n开始查询");
        List<UserInfo> userInfos = searchFunction(search);
        LocalDateTime localDate3 = LocalDateTime.now();
        System.out.println("查询结束 耗时" + localDate2.until(localDate3, ChronoUnit.MILLIS) + "毫秒 结果数为" + userInfos.size());
    }


    private static List<UserInfo> searchFunction(Search search) {
        Stream<UserInfoCathe2> stream = userInfoList.parallelStream();
        // Date条件
        if (search.getStartDate() != null && search.getEndDate() != null) {
            stream = stream.filter(c -> c.getBirthday().getTime() >= search.getStartDate().getTime() && c.getBirthday().getTime() <= search.getEndDate().getTime());
        }
        // Sex条件
        if (search.getSex() != null) {
            stream = stream.filter(c -> c.getSex().equals(search.getSex()));
        }
        // Type条件
        if (search.getType().size() > 0) {
            stream = stream.filter(c -> c.getUserTypeCathe().stream().anyMatch(cc -> search.getType().contains(cc.getType())));
        }
        // Code条件
        if (search.getCode().size() > 0) {
            stream = stream.filter(c -> c.getUserCodeCathe().stream().anyMatch(cc -> search.getCode().contains(cc.getCode())));
        }
        return stream.distinct().map(UserInfo::new).collect(Collectors.toList());
    }
}
