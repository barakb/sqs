package com.totango.sqs

import kotlinx.coroutines.future.await
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider
import software.amazon.awssdk.core.util.DefaultSdkAutoConstructList
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sqs.SqsAsyncClient
import software.amazon.awssdk.services.sqs.model.*
import java.io.Closeable


@Suppress("unused")
class Sqs(region: Region) : Closeable {

    private val sqsClient: SqsAsyncClient = SqsAsyncClient.builder()
        .region(region)
        .credentialsProvider(DefaultCredentialsProvider.create())
        .build()

    suspend fun createQueue(queueName: String): String {
        val request = CreateQueueRequest.builder()
            .queueName(queueName)
            .build()
        return sqsClient.createQueue(request).await().queueUrl()
    }

    suspend fun getQueueUrl(queueName: String): String {
        val getQueueRequest = GetQueueUrlRequest.builder()
            .queueName(queueName)
            .build()
        return sqsClient.getQueueUrl(getQueueRequest).await().queueUrl()
    }

    suspend fun sendMessage(queueUrl: String, message: String): SendMessageResponse {
        val sendMsgRequest = SendMessageRequest.builder()
            .queueUrl(queueUrl)
            .messageBody(message)
            .delaySeconds(5)
            .build()
        return sqsClient.sendMessage(sendMsgRequest).await()
    }

    suspend fun receiveMessages(
        queueUrl: String,
        attributeNames: List<QueueAttributeName> = DefaultSdkAutoConstructList.getInstance(),
        messageAttributeNames: List<String> = DefaultSdkAutoConstructList.getInstance(),
        maxNumberOfMessages: Int? = null,
        visibilityTimeout: Int? = null,
        waitTimeSeconds: Int? = null,
        receiveRequestAttemptId: String? = null
    ): MutableList<Message> {
        val receiveRequest = ReceiveMessageRequest.builder()
            .queueUrl(queueUrl)
            .attributeNames(attributeNames)
            .messageAttributeNames(messageAttributeNames)
            .maxNumberOfMessages(maxNumberOfMessages)
            .visibilityTimeout(visibilityTimeout)
            .waitTimeSeconds(waitTimeSeconds)
            .receiveRequestAttemptId(receiveRequestAttemptId)
            .build()
        return sqsClient.receiveMessage(receiveRequest).await().messages()
    }


    override fun close() {
        sqsClient.close()
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(Sqs::class.java)
    }
}