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
package io.lenses.streamreactor.connect.aws.s3.sink.config

import io.lenses.streamreactor.connect.aws.s3.model.location.S3LocationValidator
import io.lenses.streamreactor.connect.cloud.common.config.ConnectorTaskId
import io.lenses.streamreactor.connect.cloud.common.config.DataStorageSettings
import io.lenses.streamreactor.connect.cloud.common.model.location.CloudLocationValidator
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.jdk.CollectionConverters.MapHasAsJava

class S3SinkConfigTest extends AnyFunSuite with Matchers {
  private implicit val connectorTaskId = ConnectorTaskId("connector", 1, 0)
  private implicit val cloudLocationValidator: CloudLocationValidator = S3LocationValidator
  test("envelope and CSV storage is not allowed") {
    val props = Map(
      "connect.s3.kcql" -> s"insert into mybucket:myprefix select * from TopicName PARTITIONBY _key STOREAS `CSV` WITHPARTITIONER=Values WITH_FLUSH_COUNT = 1 PROPERTIES('${DataStorageSettings.StoreEnvelopeKey}'=true)",
    )

    SinkBucketOptions(SinkConfigDefBuilder(props.asJava)) match {
      case Left(value) => value.getMessage shouldBe "Envelope is not supported for format CSV."
      case Right(_)    => fail("Should fail since envelope and CSV storage is not allowed")
    }
  }

  test("envelope and Parquet storage is allowed") {
    val props = Map(
      "connect.s3.kcql" -> s"insert into mybucket:myprefix select * from TopicName PARTITIONBY _key STOREAS `Parquet` WITHPARTITIONER=Values WITH_FLUSH_COUNT = 1 PROPERTIES('${DataStorageSettings.StoreEnvelopeKey}'=true)",
    )

    SinkBucketOptions(SinkConfigDefBuilder(props.asJava)) match {
      case Left(error) => fail("Should not fail since envelope and Parquet storage is allowed", error)
      case Right(_)    => succeed
    }
  }
  test("envelope and Avro storage is allowed") {
    val props = Map(
      "connect.s3.kcql" -> s"insert into mybucket:myprefix select * from TopicName PARTITIONBY _key STOREAS `Avro` WITHPARTITIONER=Values WITH_FLUSH_COUNT = 1 PROPERTIES('${DataStorageSettings.StoreEnvelopeKey}'=true)",
    )

    SinkBucketOptions(SinkConfigDefBuilder(props.asJava)) match {
      case Left(error) => fail("Should not fail since envelope and Avro storage is allowed", error)
      case Right(_)    => succeed
    }
  }
  test("envelope and Json storage is allowed") {
    val props = Map(
      "connect.s3.kcql" -> s"insert into mybucket:myprefix select * from TopicName PARTITIONBY _key STOREAS `Json` WITHPARTITIONER=Values WITH_FLUSH_COUNT = 1 PROPERTIES('${DataStorageSettings.StoreEnvelopeKey}'=true)",
    )

    SinkBucketOptions(SinkConfigDefBuilder(props.asJava)) match {
      case Left(error) => fail("Should not fail since envelope and Json storage is allowed", error)
      case Right(_)    => succeed
    }
  }
  test("text and envelope storage is not allowed") {
    val props = Map(
      "connect.s3.kcql" -> s"insert into mybucket:myprefix select * from TopicName PARTITIONBY _key STOREAS `Text` WITHPARTITIONER=Values WITH_FLUSH_COUNT = 1 PROPERTIES('${DataStorageSettings.StoreEnvelopeKey}'=true)",
    )

    SinkBucketOptions(SinkConfigDefBuilder(props.asJava)) match {
      case Left(value) => value.getMessage shouldBe "Envelope is not supported for format TEXT."
      case Right(_)    => fail("Should fail since text and envelope storage is not allowed")
    }
  }
  test("envelope and bytes storage is not allowed") {
    val props = Map(
      "connect.s3.kcql" -> s"insert into mybucket:myprefix select * from TopicName PARTITIONBY _key STOREAS `Bytes` WITHPARTITIONER=Values WITH_FLUSH_COUNT = 1 PROPERTIES('${DataStorageSettings.StoreEnvelopeKey}'=true)",
    )

    SinkBucketOptions(SinkConfigDefBuilder(props.asJava)) match {
      case Left(value) => value.getMessage shouldBe "Envelope is not supported for format BYTES."
      case Right(_)    => fail("Should fail since envelope and bytes storage is not allowed")
    }
  }
}
