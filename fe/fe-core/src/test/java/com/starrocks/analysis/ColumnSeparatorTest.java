// This file is made available under Elastic License 2.0.
// This file is based on code available under the Apache license here:
//   https://github.com/apache/incubator-doris/blob/master/fe/fe-core/src/test/java/org/apache/doris/analysis/ColumnSeparatorTest.java

// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

package com.starrocks.analysis;

import com.starrocks.common.AnalysisException;
import org.junit.Assert;
import org.junit.Test;

public class ColumnSeparatorTest {
    @Test
    public void testNormal() throws AnalysisException {

        ColumnSeparator separator = new ColumnSeparator("\t");
        separator.analyze();
        Assert.assertEquals("'\t'", separator.toSql());
        Assert.assertEquals("\t", separator.getColumnSeparator());

        separator = new ColumnSeparator("\\x01");
        separator.analyze();
        Assert.assertEquals("'\\x01'", separator.toSql());
        Assert.assertEquals("\1", separator.getColumnSeparator());

        separator = new ColumnSeparator("0");
        separator.analyze();
        Assert.assertEquals("'0'", separator.toSql());
        Assert.assertEquals("0", separator.getColumnSeparator());

        separator = new ColumnSeparator("0x");
        separator.analyze();
        Assert.assertEquals("'0x'", separator.toSql());
        Assert.assertEquals("0x", separator.getColumnSeparator());

        separator = new ColumnSeparator("0x1");
        separator.analyze();
        Assert.assertEquals("'0x1'", separator.toSql());
        Assert.assertEquals("0x1", separator.getColumnSeparator());

        separator = new ColumnSeparator("0x1");
        separator.analyze();
        Assert.assertEquals("'0x1'", separator.toSql());
        Assert.assertEquals("0x1", separator.getColumnSeparator());

        separator = new ColumnSeparator("\\x");
        separator.analyze();
        Assert.assertEquals("'\\x'", separator.toSql());
        Assert.assertEquals("\\x", separator.getColumnSeparator());

        separator = new ColumnSeparator("\\x1");
        separator.analyze();
        Assert.assertEquals("'\\x1'", separator.toSql());
        Assert.assertEquals("\\x1", separator.getColumnSeparator());

        separator = new ColumnSeparator("\\x0001");
        separator.analyze();
        Assert.assertEquals("'\\x0001'", separator.toSql());
        Assert.assertEquals("\u000001", separator.getColumnSeparator());

        separator = new ColumnSeparator("\\x011");
        separator.analyze();
        Assert.assertEquals("'\\x011'", separator.toSql());
        Assert.assertEquals("\u00011", separator.getColumnSeparator());

        separator = new ColumnSeparator("\\|");
        separator.analyze();
        Assert.assertEquals("'\\|'", separator.toSql());
        Assert.assertEquals("\\|", separator.getColumnSeparator());

        separator = new ColumnSeparator("\t\b");
        separator.analyze();
        Assert.assertEquals("'\t\b'", separator.toSql());
        Assert.assertEquals("\t\b", separator.getColumnSeparator());
    }

    @Test(expected = AnalysisException.class)
    public void testHexFormatError() throws AnalysisException {
        ColumnSeparator separator = new ColumnSeparator("\\x0g");
        separator.analyze();
    }

}