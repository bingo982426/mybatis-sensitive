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
 * JMH 查询基准测试
 */
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 3)
@Measurement(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
@Threads(value = 1)
@Fork(value = 1)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class JMHUpdateTest {

  WaLaLaMapper waLaLaMapper;
  LueLueLueMapper lueLueLueMapper;

  public static void main(String[] args) throws RunnerException {
    Options options = new OptionsBuilder().include(JMHUpdateTest.class.getName() + ".*")
      .output("/Volumes/zen/xxx/jmh_update.txt")
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
  public void encryptUpdateBatchTest() {
    List<WaLaLa> waLaLaList = new ArrayList<>();
    for (int index = 1001; index <= 2001; index++) {
      WaLaLa waLaLa = new WaLaLa();
      waLaLa.setId(index);
      waLaLa.setIdNo("11011319901016058X");
      waLaLaList.add(waLaLa);
    }
    waLaLaMapper.updateBatchByPrimaryKeySelective(waLaLaList);
  }

  @Benchmark
  public void updateBatchTest() {
    List<LueLueLue> lueLueLueList = new ArrayList<>();
    for (int index = 1001; index <= 2001; index++) {
      LueLueLue lueLueLue = new LueLueLue();
      lueLueLue.setId(index);
      lueLueLue.setIdNo("11011319901016058X");
      lueLueLueList.add(lueLueLue);
    }
    lueLueLueMapper.updateBatchByPrimaryKeySelective(lueLueLueList);
  }

  @Benchmark
  public void encryptUpdateOneTest() {
    WaLaLa waLaLa = new WaLaLa();
    waLaLa.setId(1001);
    waLaLa.setIdNo("11011319901016058X");
    waLaLaMapper.updateByPrimaryKeySelective(waLaLa);
  }

  @Benchmark
  public void updateOneTest() {
    LueLueLue lueLueLue = new LueLueLue();
    lueLueLue.setId(1001);
    lueLueLue.setIdNo("11011319901016058X");
    lueLueLueMapper.updateByPrimaryKeySelective(lueLueLue);
  }
}