package com.example.call_data.web_socket.custom_scarlet_adapters

import com.tinder.scarlet.Stream
import com.tinder.scarlet.StreamAdapter
import com.tinder.scarlet.utils.getRawType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.callbackFlow
import java.lang.reflect.Type

@ExperimentalCoroutinesApi
class FlowStreamAdapter<T> : StreamAdapter<T, Flow<T>> {

    override fun adapt(stream: Stream<T>): Flow<T> {
        return callbackFlow {
            stream.start(object : Stream.Observer<T> {
                override fun onComplete() {
                    close()
                }

                override fun onError(throwable: Throwable) {
                    close(cause = throwable)
                }

                override fun onNext(data: T) {
                        trySend(data)
                }
            })
            awaitClose {  }
        }.buffer(Channel.UNLIMITED)
    }

    object Factory : StreamAdapter.Factory {
        override fun create(type: Type): StreamAdapter<Any, Any> {
            return when(type.getRawType()) {
                Flow::class.java -> FlowStreamAdapter()
                else -> throw IllegalStateException("Invalid stream adapter")
            }
        }
    }
}