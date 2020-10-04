package com.practice.springboot.web;

import com.practice.springboot.domain.posts.Posts;
import com.practice.springboot.domain.posts.PostsRepository;
import com.practice.springboot.web.dto.PostsSaveRequestDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class IndexControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private PostsRepository postsRepository;

    @Test
    public void loadMainPage(){
        //when
        String body = this.restTemplate.getForObject("/", String.class);
        //then
        assertThat(body).contains("스프링 부트로 시작하는 웹 서비스");
    }
    @Test
    public void savePage(){
        //when
        String body = this.restTemplate.getForObject("/posts/save", String.class);
        //then
        assertThat(body).contains("게시글 등록");
    }
    @Test
    public void updatePage(){
        //given
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        Long updateId = savedPosts.getId();

        //when
        String body = this.restTemplate.getForObject("/posts/update/" + updateId, String.class);
        //then
        assertThat(body).contains("게시글 수정");
    }
}
