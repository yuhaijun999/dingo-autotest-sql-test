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
    @DataProvider (name = "mysqlDQLData0", parallel = true)
    public Object[][] mysqlDQLCaseGroup0() throws IOException, InterruptedException {
        String excelPathLSM = "src/test/resources/io.dingodb.test/testdata/mysqlcases/dql/casegroup1/mysql_dql_cases0.xlsx";
        String excelPathTXNLSM = "src/test/resources/io.dingodb.test/testdata/txnlsmmysqlcases/dql/casegroup1/mysql_dql_cases0_txnlsm.xlsx";
        String excelPathBTREE = "src/test/resources/io.dingodb.test/testdata/btreemysqlcases/dql/casegroup1/mysql_dql_cases0_btree.xlsx";
        String excelPathTXNBTREE = "src/test/resources/io.dingodb.test/testdata/txnbtreemysqlcases/dql/casegroup1/mysql_dql_cases0_txnbtree.xlsx";
        return getMultiEngineCasesData(excelPathLSM, excelPathTXNLSM, excelPathBTREE, excelPathTXNBTREE);
    }

    @DataProvider (name = "mysqlDQLData1", parallel = true)
    public Object[][] mysqlDQLCaseGroup1() throws IOException, InterruptedException {
        String excelPathLSM = "src/test/resources/io.dingodb.test/testdata/mysqlcases/dql/casegroup1/mysql_dql_cases1.xlsx";
        String excelPathTXNLSM = "src/test/resources/io.dingodb.test/testdata/txnlsmmysqlcases/dql/casegroup1/mysql_dql_cases1_txnlsm.xlsx";
        String excelPathBTREE = "src/test/resources/io.dingodb.test/testdata/btreemysqlcases/dql/casegroup1/mysql_dql_cases1_btree.xlsx";
        String excelPathTXNBTREE = "src/test/resources/io.dingodb.test/testdata/txnbtreemysqlcases/dql/casegroup1/mysql_dql_cases1_txnbtree.xlsx";
        return getMultiEngineCasesData(excelPathLSM, excelPathTXNLSM, excelPathBTREE, excelPathTXNBTREE);
    }

    @DataProvider (name = "mysqlDQLData2", parallel = true)
    public Object[][] mysqlDQLCaseGroup2() throws IOException, InterruptedException {
        String excelPath = "src/test/resources/io.dingodb.test/testdata/mysqlcases/dql/casegroup2/mysql_dql_cases2.xlsx";
        return getSingleEngineCasesData(excelPath);
    }
    
    @DataProvider (name = "mysqlDMLInsertData", parallel = true)
    public Object[][] mysqlDMLInsertCases() throws IOException, InterruptedException {
        String excelPathLSM = "src/test/resources/io.dingodb.test/testdata/mysqlcases/dml/insert/mysql_insert_cases.xlsx";
        String excelPathTXNLSM = "src/test/resources/io.dingodb.test/testdata/txnlsmmysqlcases/dml/insert/mysql_insert_cases_txnlsm.xlsx";
        String excelPathBTREE = "src/test/resources/io.dingodb.test/testdata/btreemysqlcases/dml/insert/mysql_insert_cases_btree.xlsx";
        String excelPathTXNBTREE = "src/test/resources/io.dingodb.test/testdata/txnbtreemysqlcases/dml/insert/mysql_insert_cases_txnbtree.xlsx";
        return getMultiEngineCasesData(excelPathLSM, excelPathTXNLSM, excelPathBTREE, excelPathTXNBTREE);
    }

    @DataProvider (name = "mysqlDMLUpDelData", parallel = true)
    public Object[][] mysqlDMLUpDelCases() throws IOException, InterruptedException {
        String excelPathLSM = "src/test/resources/io.dingodb.test/testdata/mysqlcases/dml/updatedelete/mysql_updatedelete_cases.xlsx";
        String excelPathTXNLSM = "src/test/resources/io.dingodb.test/testdata/txnlsmmysqlcases/dml/updatedelete/mysql_updatedelete_cases_txnlsm.xlsx";
        String excelPathBTREE = "src/test/resources/io.dingodb.test/testdata/btreemysqlcases/dml/updatedelete/mysql_updatedelete_cases_btree.xlsx";
        String excelPathTXNBTREE = "src/test/resources/io.dingodb.test/testdata/txnbtreemysqlcases/dml/updatedelete/mysql_updatedelete_cases_txnbtree.xlsx";
        return getMultiEngineCasesData(excelPathLSM, excelPathTXNLSM, excelPathBTREE, excelPathTXNBTREE);
    }

    @DataProvider (name = "mysqlDDLData1", parallel = true)
    public Object[][] mysqlDDLCases1() throws IOException, InterruptedException {
        String excelPathLSM = "src/test/resources/io.dingodb.test/testdata/mysqlcases/ddl/mysql_ddl_cases.xlsx";
        String excelPathTXNLSM = "src/test/resources/io.dingodb.test/testdata/txnlsmmysqlcases/ddl/mysql_ddl_cases_txnlsm.xlsx";
        String excelPathBTREE = "src/test/resources/io.dingodb.test/testdata/btreemysqlcases/ddl/mysql_ddl_cases_btree.xlsx";
        String excelPathTXNBTREE = "src/test/resources/io.dingodb.test/testdata/txnbtreemysqlcases/ddl/mysql_ddl_cases_txnbtree.xlsx";
        return getMultiEngineCasesData(excelPathLSM, excelPathTXNLSM, excelPathBTREE, excelPathTXNBTREE);
    }

    @DataProvider (name = "mysqlDCLData", parallel = false)
    public Object[][] mysqlDCLCases() throws IOException, InterruptedException {
        String excelPath = "src/test/resources/io.dingodb.test/testdata/mysqlcases/dcl/mysql_dcl_cases.xlsx";
        return getSingleEngineCasesData(excelPath);
    }

    @DataProvider (name = "mysqlSQLBatchData", parallel = true)
    public Object[][] mysqlSQLBatchCases() throws IOException, InterruptedException {
        String excelPathLSM = "src/test/resources/io.dingodb.test/testdata/mysqlcases/batchsql/mysql_batchsql_cases.xlsx";
        String excelPathTXNLSM = "src/test/resources/io.dingodb.test/testdata/txnlsmmysqlcases/batchsql/mysql_batchsql_cases_txnlsm.xlsx";
        String excelPathBTREE = "src/test/resources/io.dingodb.test/testdata/btreemysqlcases/batchsql/mysql_batchsql_cases_btree.xlsx";
        String excelPathTXNBTREE = "src/test/resources/io.dingodb.test/testdata/txnbtreemysqlcases/batchsql/mysql_batchsql_cases_txnbtree.xlsx";
        return getMultiEngineCasesData(excelPathLSM, excelPathTXNLSM, excelPathBTREE, excelPathTXNBTREE);
    }

    @DataProvider (name = "mysqlProtocolData", parallel = true)
    public Object[][] mysqlProtocolCases() throws IOException, InterruptedException {
        String excelPathLSM = "src/test/resources/io.dingodb.test/testdata/mysqlcases/protocol/mysql_protocol_cases.xlsx";
        String excelPathTXNLSM = "src/test/resources/io.dingodb.test/testdata/txnlsmmysqlcases/protocol/mysql_protocol_cases_txnlsm.xlsx";
        String excelPathBTREE = "src/test/resources/io.dingodb.test/testdata/btreemysqlcases/protocol/mysql_protocol_cases_btree.xlsx";
        String excelPathTXNBTREE = "src/test/resources/io.dingodb.test/testdata/txnbtreemysqlcases/protocol/mysql_protocol_cases_txnbtree.xlsx";
        return getMultiEngineCasesData(excelPathLSM, excelPathTXNLSM, excelPathBTREE, excelPathTXNBTREE);
    }
    
    @DataProvider (name = "mysqlNegativeData", parallel = false)
    public Object[][] mysqlNegativeCases() throws IOException, InterruptedException {
        String excelPathLSM = "src/test/resources/io.dingodb.test/testdata/mysqlcases/negative/mysql_negative_cases.xlsx";
        String excelPathTXNLSM = "src/test/resources/io.dingodb.test/testdata/txnlsmmysqlcases/negative/mysql_negative_cases_txnlsm.xlsx";
        String excelPathBTREE = "src/test/resources/io.dingodb.test/testdata/btreemysqlcases/negative/mysql_negative_cases_btree.xlsx";
        String excelPathTXNBTREE = "src/test/resources/io.dingodb.test/testdata/txnbtreemysqlcases/negative/mysql_negative_cases_txnbtree.xlsx";
        return getMultiEngineCasesData(excelPathLSM, excelPathTXNLSM, excelPathBTREE, excelPathTXNBTREE);
    }

    @DataProvider (name = "mysqlPSDQLData", parallel = true)
    public Object[][] mysqlPSDQLCases() throws IOException, InterruptedException {
        String excelPathLSM = "src/test/resources/io.dingodb.test/testdata/mysqlcases/prepareStatement/mysql_ps_dql_cases.xlsx";
        String excelPathTXNLSM = "src/test/resources/io.dingodb.test/testdata/txnlsmmysqlcases/prepareStatement/mysql_ps_dql_cases_txnlsm.xlsx";
        String excelPathBTREE = "src/test/resources/io.dingodb.test/testdata/btreemysqlcases/prepareStatement/mysql_ps_dql_cases_btree.xlsx";
        String excelPathTXNBTREE = "src/test/resources/io.dingodb.test/testdata/txnbtreemysqlcases/prepareStatement/mysql_ps_dql_cases_txnbtree.xlsx";
        return getMultiEngineCasesData(excelPathLSM, excelPathTXNLSM, excelPathBTREE, excelPathTXNBTREE);
    }

    @DataProvider (name = "mysqlPSDMLData", parallel = false)
    public Object[][] mysqlPSDMLCases() throws IOException, InterruptedException {
        String excelPathLSM = "src/test/resources/io.dingodb.test/testdata/mysqlcases/prepareStatement/mysql_ps_dml_cases.xlsx";
        String excelPathTXNLSM = "src/test/resources/io.dingodb.test/testdata/txnlsmmysqlcases/prepareStatement/mysql_ps_dml_cases_txnlsm.xlsx";
        String excelPathBTREE = "src/test/resources/io.dingodb.test/testdata/btreemysqlcases/prepareStatement/mysql_ps_dml_cases_btree.xlsx";
        String excelPathTXNBTREE = "src/test/resources/io.dingodb.test/testdata/txnbtreemysqlcases/prepareStatement/mysql_ps_dml_cases_txnbtree.xlsx";
        return getMultiEngineCasesData(excelPathLSM, excelPathTXNLSM, excelPathBTREE, excelPathTXNBTREE);
    }

    @DataProvider (name = "mysqlPSBatchData", parallel = false)
    public Object[][] mysqlPSBatchCases() throws IOException, InterruptedException {
        String excelPathLSM = "src/test/resources/io.dingodb.test/testdata/mysqlcases/prepareStatement/mysql_ps_batch_cases.xlsx";
        String excelPathTXNLSM = "src/test/resources/io.dingodb.test/testdata/txnlsmmysqlcases/prepareStatement/mysql_ps_batch_cases_txnlsm.xlsx";
        String excelPathBTREE = "src/test/resources/io.dingodb.test/testdata/btreemysqlcases/prepareStatement/mysql_ps_batch_cases_btree.xlsx";
        String excelPathTXNBTREE = "src/test/resources/io.dingodb.test/testdata/txnbtreemysqlcases/prepareStatement/mysql_ps_batch_cases_txnbtree.xlsx";
        return getMultiEngineCasesData(excelPathLSM, excelPathTXNLSM, excelPathBTREE, excelPathTXNBTREE);
    }

    @DataProvider (name = "mysqlPSBlobData", parallel = false)
    public Object[][] mysqlPSBlobCases() throws IOException, InterruptedException {
        String excelPathLSM = "src/test/resources/io.dingodb.test/testdata/mysqlcases/prepareStatement/mysql_ps_blob_cases.xlsx";
        String excelPathTXNLSM = "src/test/resources/io.dingodb.test/testdata/txnlsmmysqlcases/prepareStatement/mysql_ps_blob_cases_txnlsm.xlsx";
        String excelPathBTREE = "src/test/resources/io.dingodb.test/testdata/btreemysqlcases/prepareStatement/mysql_ps_blob_cases_btree.xlsx";
        String excelPathTXNBTREE = "src/test/resources/io.dingodb.test/testdata/txnbtreemysqlcases/prepareStatement/mysql_ps_blob_cases_txnbtree.xlsx";
        return getMultiEngineCasesData(excelPathLSM, excelPathTXNLSM, excelPathBTREE, excelPathTXNBTREE);
    }
    
}
