/*
 * Copyright 2017-2023 Lenses.io Ltd
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
package com.datamountaineer.streamreactor.connect.kudu

import com.datamountaineer.streamreactor.connect.kudu.config.KuduConfig
import com.datamountaineer.streamreactor.connect.kudu.config.KuduConfigConstants

/**
  * Created by andrew@datamountaineer.com on 24/02/16.
  * stream-reactor
  */
class TestKuduSinkConfig extends TestBase {
  "A KuduSinkConfig should return Kudu Master" in {
    val config = new KuduConfig(getConfig)
    config.getString(KuduConfigConstants.KUDU_MASTER) shouldBe KUDU_MASTER
    config.getString(KuduConfigConstants.KCQL) shouldBe KCQL_MAP
  }
}
