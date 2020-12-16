package com.totango.sqs

import kotlinx.coroutines.runBlocking
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import software.amazon.awssdk.regions.Region
import kotlin.test.Test


class SqsTest {

    @Test
    fun testCreateSendReceive(): Unit = runBlocking{
        val queue = "barak"
        Sqs(Region.US_EAST_1).use{ client ->
            val queueUrl = client.createQueue(queue)
            logger.info("queueUrl: $queueUrl")
            val sendRes = client.sendMessage(queueUrl, "message")
            logger.info("sendRes: $sendRes")
            val receiveRes = client.receiveMessages(queueUrl = queueUrl, waitTimeSeconds = 5)
            logger.info("receiveRes: $receiveRes")
        }
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(SqsTest::class.java)
    }
}