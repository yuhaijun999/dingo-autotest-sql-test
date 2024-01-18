/*
 * Copyright 2021 DataCanvas
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package datahelper;

import org.testng.annotations.DataProvider;

import java.io.IOException;

public class MySQLYamlDataHelper extends BaseDataHelper{

    @DataProvider (name = "mysqlDQLData1", parallel = false)
    public Object[][] mysqlDQLCaseGroup1() throws IOException, InterruptedException {
        String excelPathLSM = "src/test/resources/io.dingodb.test/testdata/mysqlcases/dql/casegroup1/mysql_dql_cases1.xlsx";
        String excelPathBTREE = "src/test/resources/io.dingodb.test/testdata/btreemysqlcases/dql/casegroup1/mysql_dql_cases1_btree.xlsx";
        return getMultiEngineCasesData(excelPathLSM, excelPathBTREE, excelPathBTREE);
    }

    @DataProvider (name = "mysqlDQLData2", parallel = false)
    public Object[][] mysqlDQLCaseGroup2() throws IOException, InterruptedException {
        String excelPath = "src/test/resources/io.dingodb.test/testdata/mysqlcases/dql/casegroup2/mysql_dql_cases2.xlsx";
        return getSingleEngineCasesData(excelPath);
    }
    
    @DataProvider (name = "mysqlDMLInsertData", parallel = true)
    public Object[][] mysqlDMLInsertCases() throws IOException, InterruptedException {
        String excelPathLSM = "src/test/resources/io.dingodb.test/testdata/mysqlcases/dml/insert/mysql_insert_cases.xlsx";
        String excelPathBTREE = "src/test/resources/io.dingodb.test/testdata/btreemysqlcases/dml/insert/mysql_insert_cases_btree.xlsx";
        return getMultiEngineCasesData(excelPathLSM, excelPathBTREE, excelPathBTREE);
    }

    @DataProvider (name = "mysqlDMLUpDelData", parallel = true)
    public Object[][] mysqlDMLUpDelCases() throws IOException, InterruptedException {
        String excelPathLSM = "src/test/resources/io.dingodb.test/testdata/mysqlcases/dml/updatedelete/mysql_updatedelete_cases.xlsx";
        String excelPathBTREE = "src/test/resources/io.dingodb.test/testdata/btreemysqlcases/dml/updatedelete/mysql_updatedelete_cases_btree.xlsx";
        return getMultiEngineCasesData(excelPathLSM, excelPathBTREE, excelPathBTREE);
    }

    @DataProvider (name = "mysqlDDLData1", parallel = true)
    public Object[][] mysqlDDLCases1() throws IOException, InterruptedException {
        String excelPathLSM = "src/test/resources/io.dingodb.test/testdata/mysqlcases/ddl/mysql_ddl_cases.xlsx";
        String excelPathBTREE = "src/test/resources/io.dingodb.test/testdata/btreemysqlcases/ddl/mysql_ddl_cases_btree.xlsx";
        return getMultiEngineCasesData(excelPathLSM, excelPathBTREE, excelPathBTREE);
    }

    @DataProvider (name = "mysqlDCLData", parallel = true)
    public Object[][] mysqlDCLCases() throws IOException, InterruptedException {
        String excelPath = "src/test/resources/io.dingodb.test/testdata/mysqlcases/dcl/mysql_dcl_cases.xlsx";
        return getSingleEngineCasesData(excelPath);
    }

    @DataProvider (name = "mysqlSQLBatchData", parallel = true)
    public Object[][] mysqlSQLBatchCases() throws IOException, InterruptedException {
        String excelPathLSM = "src/test/resources/io.dingodb.test/testdata/mysqlcases/batchsql/mysql_batchsql_cases.xlsx";
        String excelPathBTREE = "src/test/resources/io.dingodb.test/testdata/btreemysqlcases/batchsql/mysql_batchsql_cases_btree.xlsx";
        return getMultiEngineCasesData(excelPathLSM, excelPathBTREE, excelPathBTREE);
    }

    @DataProvider (name = "mysqlProtocolData", parallel = false)
    public Object[][] mysqlProtocolCases() throws IOException, InterruptedException {
        String excelPathLSM = "src/test/resources/io.dingodb.test/testdata/mysqlcases/protocol/mysql_protocol_cases.xlsx";
        String excelPathBTREE = "src/test/resources/io.dingodb.test/testdata/btreemysqlcases/protocol/mysql_protocol_cases_btree.xlsx";
        return getMultiEngineCasesData(excelPathLSM, excelPathBTREE, excelPathBTREE);
    }
    
    @DataProvider (name = "mysqlNegativeData", parallel = false)
    public Object[][] mysqlNegativeCases() throws IOException, InterruptedException {
        String excelPathLSM = "src/test/resources/io.dingodb.test/testdata/mysqlcases/negative/mysql_negative_cases.xlsx";
        String excelPathBTREE = "src/test/resources/io.dingodb.test/testdata/btreemysqlcases/negative/mysql_negative_cases_btree.xlsx";
        return getMultiEngineCasesData(excelPathLSM, excelPathBTREE, excelPathBTREE);
    }

    @DataProvider (name = "mysqlPSDQLData", parallel = true)
    public Object[][] mysqlPSDQLCases() throws IOException, InterruptedException {
        String excelPathLSM = "src/test/resources/io.dingodb.test/testdata/mysqlcases/prepareStatement/mysql_ps_dql_cases.xlsx";
        String excelPathBTREE = "src/test/resources/io.dingodb.test/testdata/btreemysqlcases/prepareStatement/mysql_ps_dql_cases_btree.xlsx";
        return getMultiEngineCasesData(excelPathLSM, excelPathBTREE, excelPathBTREE);
    }

    @DataProvider (name = "mysqlPSDMLData", parallel = true)
    public Object[][] mysqlPSDMLCases() throws IOException, InterruptedException {
        String excelPathLSM = "src/test/resources/io.dingodb.test/testdata/mysqlcases/prepareStatement/mysql_ps_dml_cases.xlsx";
        String excelPathBTREE = "src/test/resources/io.dingodb.test/testdata/btreemysqlcases/prepareStatement/mysql_ps_dml_cases_btree.xlsx";
        return getMultiEngineCasesData(excelPathLSM, excelPathBTREE, excelPathBTREE);
    }

    @DataProvider (name = "mysqlPSBatchData", parallel = false)
    public Object[][] mysqlPSBatchCases() throws IOException, InterruptedException {
        String excelPathLSM = "src/test/resources/io.dingodb.test/testdata/mysqlcases/prepareStatement/mysql_ps_batch_cases.xlsx";
        String excelPathBTREE = "src/test/resources/io.dingodb.test/testdata/btreemysqlcases/prepareStatement/mysql_ps_batch_cases_btree.xlsx";
        return getMultiEngineCasesData(excelPathLSM, excelPathBTREE, excelPathBTREE);
    }

    @DataProvider (name = "mysqlPSBlobData", parallel = false)
    public Object[][] mysqlPSBlobCases() throws IOException, InterruptedException {
        String excelPathLSM = "src/test/resources/io.dingodb.test/testdata/mysqlcases/prepareStatement/mysql_ps_blob_cases.xlsx";
        String excelPathBTREE = "src/test/resources/io.dingodb.test/testdata/btreemysqlcases/prepareStatement/mysql_ps_blob_cases_btree.xlsx";
        return getMultiEngineCasesData(excelPathLSM, excelPathBTREE, excelPathBTREE);
    }
    
}
