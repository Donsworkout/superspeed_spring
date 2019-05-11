package me.donsdev.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.donsdev.domain.Answer;
import me.donsdev.domain.AnswerRepository;
import me.donsdev.domain.Question;
import me.donsdev.domain.QuestionRepository;
import me.donsdev.domain.User;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    // 답변 하기
    @PostMapping
    public Answer create(@PathVariable Long questionId, String contents, HttpSession session) {
        // 로그인되어 있지 않으면 로그인 페이지로
        if ( !HttpSessionUtils.isLoginUser(session) ) {
            return null;
        }
        // 로그인된 회원의 정보 가져오기
        User loginUser = HttpSessionUtils.currentUser(session);
        Question question = questionRepository.findById(questionId).orElse(null);
        Answer answer = new Answer(contents, loginUser, question);
        return answerRepository.save(answer);
    }
    // 답변 삭제하기
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
        if ( !HttpSessionUtils.isLoginUser(session) ) {
            return Result.fail("로그인해야 합니다.");
        }

        Answer answer = answerRepository.findById(id).orElse(null);
        if ( !answer.isMyAnswer(session) ) {
            return Result.fail("자신의 글만 삭제할 수 있습니다.");
        }
        answerRepository.delete(answer);
        Question question = questionRepository.findById(questionId).orElse(null);
        questionRepository.save(question);
        return Result.ok();
    }
}