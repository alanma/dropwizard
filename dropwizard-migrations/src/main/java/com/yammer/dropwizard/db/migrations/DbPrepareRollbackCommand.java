package com.yammer.dropwizard.db.migrations;

import com.yammer.dropwizard.config.Configuration;
import liquibase.Liquibase;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparser;

import java.io.PrintWriter;

public class DbPrepareRollbackCommand<T extends Configuration> extends AbstractLiquibaseCommand<T> {
    public DbPrepareRollbackCommand(ConfigurationStrategy<T> strategy, Class<T> configurationClass) {
        super("prepare-rollback", "Generate rollback DDL scripts for pending change sets.", strategy, configurationClass);
    }

    @Override
    public void configure(Subparser subparser) {
        super.configure(subparser);

        subparser.addArgument("-c", "--count")
                 .dest("count")
                 .type(Integer.class)
                 .help("Limit script to the specified number of pending change sets");
    }

    @Override
    @SuppressWarnings("UseOfSystemOutOrSystemErr")
    public void run(Namespace namespace, Liquibase liquibase) throws Exception {
        final Integer count = namespace.getInt("count");
        if (count != null) {
            liquibase.futureRollbackSQL(count, "", new PrintWriter(System.out));
        } else {
            liquibase.futureRollbackSQL("", new PrintWriter(System.out));
        }
    }
}
