package cc.concurrent.mango.benchmark;

import cc.concurrent.mango.Mango;
import cc.concurrent.mango.MethodStats;
import cc.concurrent.mango.benchmark.model.Stat;
import cc.concurrent.mango.benchmark.round.JdbcUpdateInStatementRound;
import cc.concurrent.mango.benchmark.round.MangoUpdateInStatementRound;
import cc.concurrent.mango.benchmark.util.Config;
import cc.concurrent.mango.benchmark.util.DataSourceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ash
 */
public class UpdateInStatementRunner {

    public static void main(String[] args) throws Exception {
        List<Stat> jdbcStats = new ArrayList<Stat>();
        List<Stat> mangoStats = new ArrayList<Stat>();
        List<Long> avgs = new ArrayList<Long>();

        int round = Config.getRound();
        for (int i = 0; i < round; i++) {
            Mango mango = new Mango(DataSourceUtil.getDataSource());
            Stat jdbcStat = new JdbcUpdateInStatementRound().run();
            Stat mangoStat = new MangoUpdateInStatementRound(mango).run();
            jdbcStats.add(jdbcStat);
            mangoStats.add(mangoStat);
            MethodStats stat = new ArrayList<MethodStats>(mango.getStatsMap().values()).get(0);
            avgs.add(stat.averageExecutePenalty());
            System.out.println("round " + (i + 1) + " over!");
        }
        System.out.println("UpdateInStatementRunner");
        System.out.println("jdbcStats=" + jdbcStats);
        System.out.println("mangoStats=" + mangoStats);
        System.out.println("mangoInnerAvgs=" + avgs);
    }
}