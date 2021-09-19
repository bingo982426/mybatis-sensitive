package cn.github.savageyo.jmh;

import cn.github.savageyo.MybatisSensitiveApplication;
import cn.github.savageyo.mapper.LueLueLueMapper;
import cn.github.savageyo.mapper.WaLaLaMapper;
import cn.github.savageyo.model.LueLueLue;
import cn.github.savageyo.model.WaLaLa;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * JMH 插入基准测试
 */
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 3)
@Measurement(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
@Threads(value = 1)
@Fork(value = 1)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class JMHInsertTest {

  WaLaLaMapper waLaLaMapper;
  LueLueLueMapper lueLueLueMapper;

  public static void main(String[] args) throws RunnerException {
    //include:指定当前这个类执行options的操作 如果你有多个options不指定其它也会执行
    Options options = new OptionsBuilder().include(JMHInsertTest.class.getName() + ".*")
      .output("/Volumes/zen/xxx/jmh_insert.txt")
      .build();

    new Runner(options).run();
  }

  @Setup(Level.Trial)
  public void init() {
    String args = "";
    //指定全局变量
    ConfigurableApplicationContext context = SpringApplication.run(
      MybatisSensitiveApplication.class, args);
    waLaLaMapper = context.getBean(WaLaLaMapper.class);
    lueLueLueMapper = context.getBean(LueLueLueMapper.class);
  }

  @Benchmark
  public void encryptInsertBatchTest() {
    List<WaLaLa> waLaLaList = new ArrayList<>();
    for (int index = 1; index <= 10; index++) {
      WaLaLa waLaLa = new WaLaLa();
      waLaLa.setName("tom" + index);
      waLaLa.setIdNo("110113199805210472");
      waLaLa.setMobile("13876688314");
      waLaLa.setBankCard("6216615283785976064");
      waLaLaList.add(waLaLa);
    }
    waLaLaMapper.insertList(waLaLaList);
  }

  @Benchmark
  public void insertBatchTest() {
    List<LueLueLue> lueLueLueList = new ArrayList<>();
    for (int index = 1; index <= 10; index++) {
      LueLueLue lueLueLue = new LueLueLue();
      lueLueLue.setName("tom" + index);
      lueLueLue.setIdNo("110113199805210472");
      lueLueLue.setMobile("13876688314");
      lueLueLue.setBankCard("6216615283785976064");
      lueLueLueList.add(lueLueLue);
    }
    lueLueLueMapper.insertList(lueLueLueList);
  }

  @Benchmark
  public void encryptInsertOneTest() {
    WaLaLa waLaLa = new WaLaLa();
    waLaLa.setName("tom");
    waLaLa.setIdNo("110113199805210472");
    waLaLa.setMobile("13876688314");
    waLaLa.setBankCard("6216615283785976064");
    waLaLaMapper.insertSelective(waLaLa);
  }

  @Benchmark
  public void insertOneTest() {
    LueLueLue lueLueLue = new LueLueLue();
    lueLueLue.setName("tom");
    lueLueLue.setIdNo("110113199805210472");
    lueLueLue.setMobile("13876688314");
    lueLueLue.setBankCard("6216615283785976064");
    lueLueLueMapper.insertSelective(lueLueLue);
  }
}