import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Java Stream API使用示例
 * 演示流的各种操作：max/min、map、collect、分组、分区、并行处理等
 */
public class Main {

	public static void main(String[] args) {
		List<Integer> list = new ArrayList<>();
		list.add(1);
		list.add(2);
		list.add(-1);
		Stream<Integer> stream = list.stream();
		System.out.println(stream.max(Integer::compare).get());

		int[] ints = {1, 2, -1};
		System.out.println(Arrays.stream(ints).min().getAsInt());

		List<User> list2 = new ArrayList<>();
		list2.add(new User(2));
		list2.add(new User(21));
		list2.add(new User(20));

		System.out.println(Collections.max(list2));
		//Collections.sort(list2);
		//list2.forEach(System.out::println);

		System.out.println("---------------------------");

		Stream<User> stream1 = list2.stream();
		//System.out.println(stream1.max(User::compareTo).get());
		stream1.map(a -> {
			a.setName("111");
			return a.getName();
		}).forEach(System.out::println);

		System.out.println("---------------------------");

		// 得到列表
		List<Integer> collect = list2.stream().map(User::getAge).collect(Collectors.toList());
		collect.forEach(System.out::println);

		// 汇总信息 IntSummaryStatistics{count=3, sum=43, min=2, average=14.333333, max=21}
		IntSummaryStatistics collect1 = list2.stream().collect(Collectors.summarizingInt(User::getAge));
		System.out.println(collect1);

		// 把满足和不满足条件分块 {false=[User{name='111', age=21}, User{name='111', age=20}], true=[User{name='111', age=2}]}
		Map<Boolean, List<User>> collect2 = list2.stream().collect(Collectors.partitioningBy(s -> s.getAge() == 2));
		System.out.println(collect2.get(true));

		// 根据条件分组 {2=[User{name='111', age=2}], 20=[User{name='111', age=20}], 21=[User{name='111', age=21}]}
		Map<Integer, List<User>> collect3 = list2.stream().collect(Collectors.groupingBy(User::getAge));
		System.out.println(collect3);

		// 根据条件分组的个数 {2=1, 20=1, 21=1}
		Map<Integer, Long> collect4 = list2.stream().collect(Collectors.groupingBy(User::getAge, Collectors.counting()));
		System.out.println(collect4);

		// 并行操作 parallel()
		List<Integer> collect5 = list2.stream().parallel().map(User::getAge).collect(Collectors.toList());
		System.out.println(collect5);

	}
}
