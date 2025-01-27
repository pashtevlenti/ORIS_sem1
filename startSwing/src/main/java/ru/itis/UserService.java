package ru.itis;

class UserService {
        private final UserRepository userRepository;

        public UserService(UserRepository userRepository) {
            this.userRepository = userRepository;
        }

        public boolean saveUser(User user) {
            return userRepository.save(user);
        }
    }