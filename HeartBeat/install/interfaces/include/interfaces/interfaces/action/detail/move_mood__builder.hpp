// generated from rosidl_generator_cpp/resource/idl__builder.hpp.em
// with input from interfaces:action/MoveMood.idl
// generated code does not contain a copyright notice

#ifndef INTERFACES__ACTION__DETAIL__MOVE_MOOD__BUILDER_HPP_
#define INTERFACES__ACTION__DETAIL__MOVE_MOOD__BUILDER_HPP_

#include <algorithm>
#include <utility>

#include "interfaces/action/detail/move_mood__struct.hpp"
#include "rosidl_runtime_cpp/message_initialization.hpp"


namespace interfaces
{

namespace action
{

namespace builder
{

class Init_MoveMood_Goal_stop_sec
{
public:
  explicit Init_MoveMood_Goal_stop_sec(::interfaces::action::MoveMood_Goal & msg)
  : msg_(msg)
  {}
  ::interfaces::action::MoveMood_Goal stop_sec(::interfaces::action::MoveMood_Goal::_stop_sec_type arg)
  {
    msg_.stop_sec = std::move(arg);
    return std::move(msg_);
  }

private:
  ::interfaces::action::MoveMood_Goal msg_;
};

class Init_MoveMood_Goal_mood
{
public:
  Init_MoveMood_Goal_mood()
  : msg_(::rosidl_runtime_cpp::MessageInitialization::SKIP)
  {}
  Init_MoveMood_Goal_stop_sec mood(::interfaces::action::MoveMood_Goal::_mood_type arg)
  {
    msg_.mood = std::move(arg);
    return Init_MoveMood_Goal_stop_sec(msg_);
  }

private:
  ::interfaces::action::MoveMood_Goal msg_;
};

}  // namespace builder

}  // namespace action

template<typename MessageType>
auto build();

template<>
inline
auto build<::interfaces::action::MoveMood_Goal>()
{
  return interfaces::action::builder::Init_MoveMood_Goal_mood();
}

}  // namespace interfaces


namespace interfaces
{

namespace action
{

namespace builder
{

class Init_MoveMood_Result_finish
{
public:
  Init_MoveMood_Result_finish()
  : msg_(::rosidl_runtime_cpp::MessageInitialization::SKIP)
  {}
  ::interfaces::action::MoveMood_Result finish(::interfaces::action::MoveMood_Result::_finish_type arg)
  {
    msg_.finish = std::move(arg);
    return std::move(msg_);
  }

private:
  ::interfaces::action::MoveMood_Result msg_;
};

}  // namespace builder

}  // namespace action

template<typename MessageType>
auto build();

template<>
inline
auto build<::interfaces::action::MoveMood_Result>()
{
  return interfaces::action::builder::Init_MoveMood_Result_finish();
}

}  // namespace interfaces


namespace interfaces
{

namespace action
{

namespace builder
{

class Init_MoveMood_Feedback_feedback
{
public:
  Init_MoveMood_Feedback_feedback()
  : msg_(::rosidl_runtime_cpp::MessageInitialization::SKIP)
  {}
  ::interfaces::action::MoveMood_Feedback feedback(::interfaces::action::MoveMood_Feedback::_feedback_type arg)
  {
    msg_.feedback = std::move(arg);
    return std::move(msg_);
  }

private:
  ::interfaces::action::MoveMood_Feedback msg_;
};

}  // namespace builder

}  // namespace action

template<typename MessageType>
auto build();

template<>
inline
auto build<::interfaces::action::MoveMood_Feedback>()
{
  return interfaces::action::builder::Init_MoveMood_Feedback_feedback();
}

}  // namespace interfaces


namespace interfaces
{

namespace action
{

namespace builder
{

class Init_MoveMood_SendGoal_Request_goal
{
public:
  explicit Init_MoveMood_SendGoal_Request_goal(::interfaces::action::MoveMood_SendGoal_Request & msg)
  : msg_(msg)
  {}
  ::interfaces::action::MoveMood_SendGoal_Request goal(::interfaces::action::MoveMood_SendGoal_Request::_goal_type arg)
  {
    msg_.goal = std::move(arg);
    return std::move(msg_);
  }

private:
  ::interfaces::action::MoveMood_SendGoal_Request msg_;
};

class Init_MoveMood_SendGoal_Request_goal_id
{
public:
  Init_MoveMood_SendGoal_Request_goal_id()
  : msg_(::rosidl_runtime_cpp::MessageInitialization::SKIP)
  {}
  Init_MoveMood_SendGoal_Request_goal goal_id(::interfaces::action::MoveMood_SendGoal_Request::_goal_id_type arg)
  {
    msg_.goal_id = std::move(arg);
    return Init_MoveMood_SendGoal_Request_goal(msg_);
  }

private:
  ::interfaces::action::MoveMood_SendGoal_Request msg_;
};

}  // namespace builder

}  // namespace action

template<typename MessageType>
auto build();

template<>
inline
auto build<::interfaces::action::MoveMood_SendGoal_Request>()
{
  return interfaces::action::builder::Init_MoveMood_SendGoal_Request_goal_id();
}

}  // namespace interfaces


namespace interfaces
{

namespace action
{

namespace builder
{

class Init_MoveMood_SendGoal_Response_stamp
{
public:
  explicit Init_MoveMood_SendGoal_Response_stamp(::interfaces::action::MoveMood_SendGoal_Response & msg)
  : msg_(msg)
  {}
  ::interfaces::action::MoveMood_SendGoal_Response stamp(::interfaces::action::MoveMood_SendGoal_Response::_stamp_type arg)
  {
    msg_.stamp = std::move(arg);
    return std::move(msg_);
  }

private:
  ::interfaces::action::MoveMood_SendGoal_Response msg_;
};

class Init_MoveMood_SendGoal_Response_accepted
{
public:
  Init_MoveMood_SendGoal_Response_accepted()
  : msg_(::rosidl_runtime_cpp::MessageInitialization::SKIP)
  {}
  Init_MoveMood_SendGoal_Response_stamp accepted(::interfaces::action::MoveMood_SendGoal_Response::_accepted_type arg)
  {
    msg_.accepted = std::move(arg);
    return Init_MoveMood_SendGoal_Response_stamp(msg_);
  }

private:
  ::interfaces::action::MoveMood_SendGoal_Response msg_;
};

}  // namespace builder

}  // namespace action

template<typename MessageType>
auto build();

template<>
inline
auto build<::interfaces::action::MoveMood_SendGoal_Response>()
{
  return interfaces::action::builder::Init_MoveMood_SendGoal_Response_accepted();
}

}  // namespace interfaces


namespace interfaces
{

namespace action
{

namespace builder
{

class Init_MoveMood_GetResult_Request_goal_id
{
public:
  Init_MoveMood_GetResult_Request_goal_id()
  : msg_(::rosidl_runtime_cpp::MessageInitialization::SKIP)
  {}
  ::interfaces::action::MoveMood_GetResult_Request goal_id(::interfaces::action::MoveMood_GetResult_Request::_goal_id_type arg)
  {
    msg_.goal_id = std::move(arg);
    return std::move(msg_);
  }

private:
  ::interfaces::action::MoveMood_GetResult_Request msg_;
};

}  // namespace builder

}  // namespace action

template<typename MessageType>
auto build();

template<>
inline
auto build<::interfaces::action::MoveMood_GetResult_Request>()
{
  return interfaces::action::builder::Init_MoveMood_GetResult_Request_goal_id();
}

}  // namespace interfaces


namespace interfaces
{

namespace action
{

namespace builder
{

class Init_MoveMood_GetResult_Response_result
{
public:
  explicit Init_MoveMood_GetResult_Response_result(::interfaces::action::MoveMood_GetResult_Response & msg)
  : msg_(msg)
  {}
  ::interfaces::action::MoveMood_GetResult_Response result(::interfaces::action::MoveMood_GetResult_Response::_result_type arg)
  {
    msg_.result = std::move(arg);
    return std::move(msg_);
  }

private:
  ::interfaces::action::MoveMood_GetResult_Response msg_;
};

class Init_MoveMood_GetResult_Response_status
{
public:
  Init_MoveMood_GetResult_Response_status()
  : msg_(::rosidl_runtime_cpp::MessageInitialization::SKIP)
  {}
  Init_MoveMood_GetResult_Response_result status(::interfaces::action::MoveMood_GetResult_Response::_status_type arg)
  {
    msg_.status = std::move(arg);
    return Init_MoveMood_GetResult_Response_result(msg_);
  }

private:
  ::interfaces::action::MoveMood_GetResult_Response msg_;
};

}  // namespace builder

}  // namespace action

template<typename MessageType>
auto build();

template<>
inline
auto build<::interfaces::action::MoveMood_GetResult_Response>()
{
  return interfaces::action::builder::Init_MoveMood_GetResult_Response_status();
}

}  // namespace interfaces


namespace interfaces
{

namespace action
{

namespace builder
{

class Init_MoveMood_FeedbackMessage_feedback
{
public:
  explicit Init_MoveMood_FeedbackMessage_feedback(::interfaces::action::MoveMood_FeedbackMessage & msg)
  : msg_(msg)
  {}
  ::interfaces::action::MoveMood_FeedbackMessage feedback(::interfaces::action::MoveMood_FeedbackMessage::_feedback_type arg)
  {
    msg_.feedback = std::move(arg);
    return std::move(msg_);
  }

private:
  ::interfaces::action::MoveMood_FeedbackMessage msg_;
};

class Init_MoveMood_FeedbackMessage_goal_id
{
public:
  Init_MoveMood_FeedbackMessage_goal_id()
  : msg_(::rosidl_runtime_cpp::MessageInitialization::SKIP)
  {}
  Init_MoveMood_FeedbackMessage_feedback goal_id(::interfaces::action::MoveMood_FeedbackMessage::_goal_id_type arg)
  {
    msg_.goal_id = std::move(arg);
    return Init_MoveMood_FeedbackMessage_feedback(msg_);
  }

private:
  ::interfaces::action::MoveMood_FeedbackMessage msg_;
};

}  // namespace builder

}  // namespace action

template<typename MessageType>
auto build();

template<>
inline
auto build<::interfaces::action::MoveMood_FeedbackMessage>()
{
  return interfaces::action::builder::Init_MoveMood_FeedbackMessage_goal_id();
}

}  // namespace interfaces

#endif  // INTERFACES__ACTION__DETAIL__MOVE_MOOD__BUILDER_HPP_
