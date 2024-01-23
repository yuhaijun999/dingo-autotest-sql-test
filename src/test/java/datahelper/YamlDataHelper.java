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

public class YamlDataHelper extends BaseDataHelper{
    @DataProvider (name = "dqlData1", parallel = false)
    public Object[][] dqlCaseGroup1() throws IOException, InterruptedException {
        String excelPathLSM = "src/test/resources/io.dingodb.test/testdata/cases/dql/casegroup1/sql_dql_cases1.xlsx";
        String excelPathTXNLSM = "src/test/resources/io.dingodb.test/testdata/txnlsmcases/dql/casegroup1/sql_dql_cases1_txnlsm.xlsx";
        String excelPathBTREE = "src/test/resources/io.dingodb.test/testdata/btreecases/dql/casegroup1/sql_dql_cases1_btree.xlsx";
        String excelPathTXNBTREE = "src/test/resources/io.dingodb.test/testdata/txnbtreecases/dql/casegroup1/sql_dql_cases1_txnbtree.xlsx";
        return getMultiEngineCasesData(excelPathLSM, excelPathTXNLSM, excelPathBTREE, excelPathTXNBTREE);
    }

    @DataProvider (name = "dqlData2", parallel = false)
    public Object[][] dqlCaseGroup2() throws IOException, InterruptedException {
        String excelPath = "src/test/resources/io.dingodb.test/testdata/cases/dql/casegroup2/sql_dql_cases2.xlsx";
        return getSingleEngineCasesData(excelPath);
    }

    @DataProvider (name = "dqlData3", parallel = true)
    public Object[][] dqlCaseGroup3() throws IOException, InterruptedException {
        String excelPathLSM = "src/test/resources/io.dingodb.test/testdata/cases/dql/casegroup3/sql_dql_cases3.xlsx";
        String excelPathTXNLSM = "src/test/resources/io.dingodb.test/testdata/txnlsmcases/dql/casegroup3/sql_dql_cases3_txnlsm.xlsx";
        String excelPathBTREE = "src/test/resources/io.dingodb.test/testdata/btreecases/dql/casegroup3/sql_dql_cases3_btree.xlsx";
        String excelPathTXNBTREE = "src/test/resources/io.dingodb.test/testdata/txnbtreecases/dql/casegroup3/sql_dql_cases3_txnbtree.xlsx";
        return getMultiEngineCasesData(excelPathLSM, excelPathTXNLSM, excelPathBTREE, excelPathTXNBTREE);
    }

    @DataProvider (name = "dmlInsertData", parallel = true)
    public Object[][] dmlInsertCases() throws IOException, InterruptedException {
        String excelPathLSM = "src/test/resources/io.dingodb.test/testdata/cases/dml/insert/sql_insert_cases.xlsx";
        String excelPathTXNLSM = "src/test/resources/io.dingodb.test/testdata/txnlsmcases/dml/insert/sql_insert_cases_txnlsm.xlsx";
        String excelPathBTREE = "src/test/resources/io.dingodb.test/testdata/btreecases/dml/insert/sql_insert_cases_btree.xlsx";
        String excelPathTXNBTREE = "src/test/resources/io.dingodb.test/testdata/txnbtreecases/dml/insert/sql_insert_cases_txnbtree.xlsx";
        return getMultiEngineCasesData(excelPathLSM, excelPathTXNLSM, excelPathBTREE, excelPathTXNBTREE);
    }

    @DataProvider (name = "dmlUpDelData", parallel = true)
    public Object[][] dmlUpDelCases() throws IOException, InterruptedException {
        String excelPathLSM = "src/test/resources/io.dingodb.test/testdata/cases/dml/updatedelete/sql_updatedelete_cases.xlsx";
        String excelPathTXNLSM = "src/test/resources/io.dingodb.test/testdata/txnlsmcases/dml/updatedelete/sql_updatedelete_cases_txnlsm.xlsx";
        String excelPathBTREE = "src/test/resources/io.dingodb.test/testdata/btreecases/dml/updatedelete/sql_updatedelete_cases_btree.xlsx";
        String excelPathTXNBTREE = "src/test/resources/io.dingodb.test/testdata/txnbtreecases/dml/updatedelete/sql_updatedelete_cases_txnbtree.xlsx";
        return getMultiEngineCasesData(excelPathLSM, excelPathTXNLSM, excelPathBTREE, excelPathTXNBTREE);
    }

    @DataProvider (name = "ddlData1", parallel = true)
    public Object[][] ddlCases1() throws IOException, InterruptedException {
        String excelPathLSM = "src/test/resources/io.dingodb.test/testdata/cases/ddl/sql_ddl_cases.xlsx";
        String excelPathTXNLSM = "src/test/resources/io.dingodb.test/testdata/txnlsmcases/ddl/sql_ddl_cases_txnlsm.xlsx";
        String excelPathBTREE = "src/test/resources/io.dingodb.test/testdata/btreecases/ddl/sql_ddl_cases_btree.xlsx";
        String excelPathTXNBTREE = "src/test/resources/io.dingodb.test/testdata/txnbtreecases/ddl/sql_ddl_cases_txnbtree.xlsx";
        return getMultiEngineCasesData(excelPathLSM, excelPathTXNLSM, excelPathBTREE, excelPathTXNBTREE);
    }

    @DataProvider (name = "dclData", parallel = false)
    public Object[][] dclCases() throws IOException, InterruptedException {
        String excelPath = "src/test/resources/io.dingodb.test/testdata/cases/dcl/sql_dcl_cases.xlsx";
        return getSingleEngineCasesData(excelPath);
    }

    @DataProvider (name = "sqlBatchData", parallel = true)
    public Object[][] sqlBatchCases() throws IOException, InterruptedException {
        String excelPathLSM = "src/test/resources/io.dingodb.test/testdata/cases/batchsql/sql_batchsql_cases.xlsx";
        String excelPathTXNLSM = "src/test/resources/io.dingodb.test/testdata/txnlsmcases/batchsql/sql_batchsql_cases_txnlsm.xlsx";
        String excelPathBTREE = "src/test/resources/io.dingodb.test/testdata/btreecases/batchsql/sql_batchsql_cases_btree.xlsx";
        String excelPathTXNBTREE = "src/test/resources/io.dingodb.test/testdata/txnbtreecases/batchsql/sql_batchsql_cases_txnbtree.xlsx";
        return getMultiEngineCasesData(excelPathLSM, excelPathTXNLSM, excelPathBTREE, excelPathTXNBTREE);
    }

    @DataProvider (name = "negativeData", parallel = false)
    public Object[][] negativeCases() throws IOException, InterruptedException {
        String excelPathLSM = "src/test/resources/io.dingodb.test/testdata/cases/negative/sql_negative_cases.xlsx";
        String excelPathTXNLSM = "src/test/resources/io.dingodb.test/testdata/txnlsmcases/negative/sql_negative_cases_txnlsm.xlsx";
        String excelPathBTREE = "src/test/resources/io.dingodb.test/testdata/btreecases/negative/sql_negative_cases_btree.xlsx";
        String excelPathTXNBTREE = "src/test/resources/io.dingodb.test/testdata/txnbtreecases/negative/sql_negative_cases_txnbtree.xlsx";
        return getMultiEngineCasesData(excelPathLSM, excelPathTXNLSM, excelPathBTREE, excelPathTXNBTREE);
    }

    @DataProvider (name = "indexData1", parallel = false)
    public Object[][] indexCases1() throws IOException, InterruptedException {
        String excelPathLSM = "src/test/resources/io.dingodb.test/testdata/cases/index/index_cases1.xlsx";
        String excelPathTXNLSM = "src/test/resources/io.dingodb.test/testdata/txnlsmcases/index/index_cases1_txnlsm.xlsx";
        String excelPathBTREE = "src/test/resources/io.dingodb.test/testdata/btreecases/index/index_cases1_btree.xlsx";
        String excelPathTXNBTREE = "src/test/resources/io.dingodb.test/testdata/txnbtreecases/index/index_cases1_txnbtree.xlsx";
        return getMultiEngineCasesData(excelPathLSM, excelPathTXNLSM, excelPathBTREE, excelPathTXNBTREE);
    }

    @DataProvider (name = "indexData2", parallel = true)
    public Object[][] indexCases2() throws IOException, InterruptedException {
        String excelPathLSM = "src/test/resources/io.dingodb.test/testdata/cases/index/index_cases2.xlsx";
        String excelPathTXNLSM = "src/test/resources/io.dingodb.test/testdata/txnlsmcases/index/index_cases2_txnlsm.xlsx";
        String excelPathBTREE = "src/test/resources/io.dingodb.test/testdata/btreecases/index/index_cases2_btree.xlsx";
        String excelPathTXNBTREE = "src/test/resources/io.dingodb.test/testdata/txnbtreecases/index/index_cases2_txnbtree.xlsx";
        return getMultiEngineCasesData(excelPathLSM, excelPathTXNLSM, excelPathBTREE, excelPathTXNBTREE);
    }

    @DataProvider (name = "psDQLData", parallel = true)
    public Object[][] psDQLCases() throws IOException, InterruptedException {
        String excelPathLSM = "src/test/resources/io.dingodb.test/testdata/cases/prepareStatement/sql_ps_dql_cases.xlsx";
        String excelPathTXNLSM = "src/test/resources/io.dingodb.test/testdata/txnlsmcases/prepareStatement/sql_ps_dql_cases_txnlsm.xlsx";
        String excelPathBTREE = "src/test/resources/io.dingodb.test/testdata/btreecases/prepareStatement/sql_ps_dql_cases_btree.xlsx";
        String excelPathTXNBTREE = "src/test/resources/io.dingodb.test/testdata/txnbtreecases/prepareStatement/sql_ps_dql_cases_txnbtree.xlsx";
        return getMultiEngineCasesData(excelPathLSM, excelPathTXNLSM, excelPathBTREE, excelPathTXNBTREE);
    }

    @DataProvider (name = "psDMLData", parallel = false)
    public Object[][] psDMLCases() throws IOException, InterruptedException {
        String excelPathLSM = "src/test/resources/io.dingodb.test/testdata/cases/prepareStatement/sql_ps_dml_cases.xlsx";
        String excelPathTXNLSM = "src/test/resources/io.dingodb.test/testdata/txnlsmcases/prepareStatement/sql_ps_dml_cases_txnlsm.xlsx";
        String excelPathBTREE = "src/test/resources/io.dingodb.test/testdata/btreecases/prepareStatement/sql_ps_dml_cases_btree.xlsx";
        String excelPathTXNBTREE = "src/test/resources/io.dingodb.test/testdata/txnbtreecases/prepareStatement/sql_ps_dml_cases_txnbtree.xlsx";
        return getMultiEngineCasesData(excelPathLSM, excelPathTXNLSM, excelPathBTREE, excelPathTXNBTREE);
    }

    @DataProvider (name = "psBatchData", parallel = false)
    public Object[][] psBatchCases() throws IOException, InterruptedException {
        String excelPathLSM = "src/test/resources/io.dingodb.test/testdata/cases/prepareStatement/sql_ps_batch_cases.xlsx";
        String excelPathTXNLSM = "src/test/resources/io.dingodb.test/testdata/txnlsmcases/prepareStatement/sql_ps_batch_cases_txnlsm.xlsx";
        String excelPathBTREE = "src/test/resources/io.dingodb.test/testdata/btreecases/prepareStatement/sql_ps_batch_cases_btree.xlsx";
        String excelPathTXNBTREE = "src/test/resources/io.dingodb.test/testdata/txnbtreecases/prepareStatement/sql_ps_batch_cases_txnbtree.xlsx";
        return getMultiEngineCasesData(excelPathLSM, excelPathTXNLSM, excelPathBTREE, excelPathTXNBTREE);
    }
    
}
