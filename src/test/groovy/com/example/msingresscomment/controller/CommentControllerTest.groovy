package com.example.msingresscomment.controller

import com.example.msingresscomment.model.constants.HeaderConstants
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
        def commentId = 1L
        def userId = 1L
        def url = "/v1/comments/$commentId"
        def request = new UpdateCommentRequest(1L, "Hello world")
        def requestBody = '''
       {
         "id": 1,
         "text": "Hello world"
         
        }
        
         '''

        when:
        def result = mockMvc.perform(put(url)
                .contentType(APPLICATION_JSON)
                .header(HeaderConstants.USER_ID, userId.toString())
                .content(requestBody)).andReturn()

        then:
        1 * commentService.updateComment(commentId, userId, request)
        def response = result.response
        response.status == NO_CONTENT.value()
    }

    def "TestDeleteComment"() {
        given:
        def commentId = 1L
        def userId = 1L
        def url = "/v1/comments/$commentId"

        when:
        def result = mockMvc.perform(delete(url)
                .header(HeaderConstants.USER_ID, userId.toString())).andReturn()

        then:
        1 * commentService.deleteComment(commentId, userId)
        def response = result.response
        response.status == NO_CONTENT.value()
    }

    def "TestGetComments"() {
        given:
        def url = "/v1/comments"

        when:
        def result = mockMvc.perform(get(url)
                .contentType(APPLICATION_JSON)).andReturn()

        then:
        1 * commentService.getCommentsByProduct()
        def response = result.response
        response.status == OK.value()
    }


}
