[![Build Status](https://www.travis-ci.com/barakb/sqs.svg?branch=master)](https://www.travis-ci.com/barakb/sqs)
### A simple non-blocking client for AWS SQS service.

##### building
gradle build

##### Example

```kotlin
   fun createSendReceive(): Unit = runBlocking{
        val queue = "barak"
        Sqs(Region.US_EAST_1).use{ client ->
            val queueUrl = client.createQueue(queue)
            val sendRes = client.sendMessage(queueUrl, "message")
            val receiveRes = client.receiveMessages(queueUrl = queueUrl, waitTimeSeconds = 5)
        }
    }
```

[SQS test](https://github.com/barakb/sqs/blob/master/lib/src/test/kotlin/com/totango/sqs/SqsTest.kt)
