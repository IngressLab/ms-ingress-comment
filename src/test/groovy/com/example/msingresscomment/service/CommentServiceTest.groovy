package com.example.msingresscomment.service

import com.example.msingresscomment.dao.entity.CommentEntity
import com.example.msingresscomment.dao.repository.CommentRepository
import com.example.msingresscomment.exception.NotFoundException
import com.example.msingresscomment.mapper.CommentMapper
import com.example.msingresscomment.model.enums.Status
import com.example.msingresscomment.model.request.SaveCommentRequest
import com.example.msingresscomment.model.request.UpdateCommentRequest
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import spock.lang.Specification

import static com.example.msingresscomment.mapper.CommentMapper.buildCommentEntity

class CommentServiceTest extends Specification {
    private EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandom()
    private CommentRepository commentRepository
    private CommentService commentService

    def setup() {
        commentRepository = Mock()
        commentService = new CommentService(commentRepository)
    }

    def "TestGetCommentById success"() {
        given:
        def id = random.nextObject(Long)
        def entity = random.nextObject(CommentEntity)

        when:
        def variable = commentService.getCommentById(id)

        then:
        1 * commentRepository.findById(id) >> Optional.of(entity)
        variable.userId == entity.userId
        variable.productId == entity.productId
        variable.text == entity.text
    }

    def "TestGetCommentById error"() {
        given:

        def id = random.nextObject(Long)

        when:
        commentService.getCommentById(id)

        then:
        1 * commentRepository.findById(id) >> Optional.empty()
        NotFoundException exception = thrown()
        exception.message == "COMMENT_NOT_FOUND"
    }

    def "TestSaveCommentRequest"() {
        given:
        def entity = random.nextObject(CommentEntity)
        def request = random.nextObject(SaveCommentRequest)
        def mapper = random.nextObject(CommentMapper)

        when:
        commentService.saveComment(request)

        then:
        1 * commentRepository.save(buildCommentEntity(request)) >> Optional.of(entity)
    }

    def "TestUpdateComment success"() {
        given:
        def commentId=random.nextObject(Long)
        def userId = random.nextObject(Long);
        def entity = random.nextObject(CommentEntity)
        def request = random.nextObject(UpdateCommentRequest)
        when:
        commentService.updateComment(commentId,userId,request)

        then:
        1 * commentRepository.findByIdAndUserId(commentId,userId)>> Optional.of(entity)
        entity.setText(request.getText())
    }

    def "TestUpdateComment error"() {
        given:
        def commentId=random.nextObject(Long)
        def userId = random.nextObject(Long)
        def request = random.nextObject(UpdateCommentRequest)

        when:
        commentService.updateComment(commentId,userId, request)

        then:
        1 * commentRepository.findByIdAndUserId(commentId,userId) >> Optional.empty()
        0 * commentRepository.save(userId)

        NotFoundException exception = thrown()
        exception.message == "COMMENT_NOT_FOUND"
    }

    def " TestDeleteComment success"() {

        given:
        def commentId = random.nextObject(Long)
        def userId=random.nextObject(Long)
        def entity = random.nextObject(CommentEntity)

        when:
        commentService.deleteComment(commentId,userId)

        then:

        1 * commentRepository.findByIdAndUserId(commentId,userId) >> Optional.of(entity)
        1 * commentRepository.save(entity)
        entity.status == Status.DELETED
    }

    def "TestDeleteComment error"() {

        given:
        def commentId = random.nextObject(Long)
        def userId = random.nextObject(Long)

        when:
        commentService.deleteComment(commentId,userId)

        then:
        1 * commentRepository.findByIdAndUserId(commentId,userId) >> Optional.empty()
        0 * commentRepository.save()
        NotFoundException exception = thrown()
        exception.message == "COMMENT_NOT_FOUND"
    }

    def "TestGetCommentsByProduct"(){
        given:
        def entity=random.nextObject(CommentEntity)
        def commentList=[entity]
        commentRepository.findAll()>>commentList

        when:
        commentService.getCommentsByProduct()

        then:
        1*commentRepository.findAll()>>commentList
    }

}
