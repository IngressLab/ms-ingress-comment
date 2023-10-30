package com.example.msingresscomment.controller

import com.example.msingresscomment.model.request.SaveCommentRequest
import com.example.msingresscomment.model.request.UpdateCommentRequest
import com.example.msingresscomment.model.response.CommentResponse
import com.example.msingresscomment.service.CommentService
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import org.skyscreamer.jsonassert.JSONAssert
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import java.time.LocalDateTime

import static org.springframework.http.HttpStatus.*
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*

class CommentControllerTest extends Specification {
    private EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandom()
    private CommentService commentService
    private MockMvc mockMvc

    void setup() {
        commentService = Mock()
        def commentController = new CommentController(commentService)
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build()
    }

    def "TestGetUserById"() {
        given:
        def id = 1L
        def url = "/v1/comments/$id"

        def responseView = new CommentResponse(1L, 1L, 1L, "Hello world")

        def expectedResponse = '''
{
   "id": 1,
   "userId": 1,
   "productId": 1,
   "text": "Hello world"
   }
'''
        when:
        def result = mockMvc.perform(get(url)
                .param("number", 1.toString())).andReturn()


        then:
        1 * commentService.getCommentById(id) >> responseView
        def response = result.response
        response.status == OK.value()
        JSONAssert.assertEquals(expectedResponse, response.getContentAsString(), false)
    }

    def "TestSaveComment"() {
        given:
        def url = "/v1/comments"
        def request = new SaveCommentRequest(1L, 1L, "Hello world")
        def requestBody = '''
      {
        "userId": 1,
        "productId": 1,
        "text": "Hello world"
        }
      '''

        when:
        def result = mockMvc.perform(post(url)
                .contentType(APPLICATION_JSON)
                .content(requestBody)).andReturn()

        then:
        1 * commentService.saveComment(request)
        def response = result.response
        response.status == CREATED.value()

    }

    def "TestUpdateComment"() {
        given:
        def userId = 1L
        def url = "/v1/comments/$userId"
        def request = new UpdateCommentRequest(1L, 1L, "Hello world", LocalDateTime.now())
        def requestBody = '''
       {
         "id": 1,
         "userId": 1,
         "text": "Hello world",
        }
         '''

        when:
        def result = mockMvc.perform(put(url)
                .contentType(APPLICATION_JSON)
                .contentType(requestBody)).andReturn()

        then:
        1 * commentService.updateComment(userId, requestBody)
        def response = result.response
        response.status == NO_CONTENT.value()
    }

    def "TestDeleteComment"() {
        given:
        def id = 1L
        def url = "/v1/comments/$id"

        when:
        def result = mockMvc.perform(delete(url)).andReturn()

        then:
        1 * commentService.deleteComment(id)
        def response = result.response
        response.status == NO_CONTENT.value()
    }


}
