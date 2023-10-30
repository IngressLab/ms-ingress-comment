package com.example.msingresscomment.mapper

import com.example.msingresscomment.dao.entity.CommentEntity
import com.example.msingresscomment.model.request.SaveCommentRequest
import com.example.msingresscomment.model.request.UpdateCommentRequest
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import spock.lang.Specification

import static com.example.msingresscomment.mapper.CommentMapper.*
import static com.example.msingresscomment.mapper.CommentMapper.*

class CommentMapperTest extends Specification {
    private EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandom()

    def "TestCommentBuildEntity"() {
        given:
        def request = random.nextObject(SaveCommentRequest)

        when:

        def variable = buildCommentEntity(request)

        then:
        variable.userId == request.userId
        variable.productId == request.productId
        variable.text == request.text
    }

    def "TestCommentBuildResponse"() {

        given:
        def entity = random.nextObject(CommentEntity)
        when:
        def variable = buildCommentResponse(entity)
        then:
        variable.text == entity.text
        variable.productId == entity.productId
        variable.userId == entity.userId
    }

    def "TestCommentUpdate"() {
        given:
        def entity = random.nextObject(CommentEntity)
        def request = random.nextObject(UpdateCommentRequest)
        when:
        updateCommentEntity(entity, request)
        then:
        entity.userId == request.userId
        entity.createdAt==request.createdAt
        entity.text == request.text
    }
}
