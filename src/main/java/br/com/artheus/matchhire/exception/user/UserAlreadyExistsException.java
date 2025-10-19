package br.com.artheus.matchhire.exception.user;

public class UserAlreadyExistsException extends RuntimeException {
  public UserAlreadyExistsException(String login) {
    super("User with login '" + login + "' already exists");
  }
}
