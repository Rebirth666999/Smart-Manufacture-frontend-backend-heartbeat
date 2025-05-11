// generated from rosidl_generator_cpp/resource/idl__builder.hpp.em
// with input from interfaces:action/MoveTime.idl
// generated code does not contain a copyright notice

#ifndef INTERFACES__ACTION__DETAIL__MOVE_TIME__BUILDER_HPP_
#define INTERFACES__ACTION__DETAIL__MOVE_TIME__BUILDER_HPP_

#include <algorithm>
#include <utility>

#include "interfaces/action/detail/move_time__struct.hpp"
#include "rosidl_runtime_cpp/message_initialization.hpp"


namespace interfaces
{

namespace action
{

namespace builder
{

class Init_MoveTime_Goal_runtime
{
public:
  Init_MoveTime_Goal_runtime()
  : msg_(::rosidl_runtime_cpp::MessageInitialization::SKIP)
  {}
  ::interfaces::action::MoveTime_Goal runtime(::interfaces::action::MoveTime_Goal::_runtime_type arg)
  {
    msg_.runtime = std::move(arg);
    return std::move(msg_);
  }

private:
  ::interfaces::action::MoveTime_Goal msg_;
};

}  // namespace builder

}  // namespace action

template<typename MessageType>
auto build();

template<>
inline
auto build<::interfaces::action::MoveTime_Goal>()
{
  return interfaces::action::builder::Init_MoveTime_Goal_runtime();
}

}  // namespace interfaces


namespace interfaces
{

namespace action
{

namespace builder
{

class Init_MoveTime_Result_finish
{
public:
  Init_MoveTime_Result_finish()
  : msg_(::rosidl_runtime_cpp::MessageInitialization::SKIP)
  {}
  ::interfaces::action::MoveTime_Result finish(::interfaces::action::MoveTime_Result::_finish_type arg)
  {
    msg_.finish = std::move(arg);
    return std::move(msg_);
  }

private:
  ::interfaces::action::MoveTime_Result msg_;
};

}  // namespace builder

}  // namespace action

template<typename MessageType>
auto build();

template<>
inline
auto build<::interfaces::action::MoveTime_Result>()
{
  return interfaces::action::builder::Init_MoveTime_Result_finish();
}

}  // namespace interfaces


namespace interfaces
{

namespace action
{

namespace builder
{

class Init_MoveTime_Feedback_feedback
{
public:
  Init_MoveTime_Feedback_feedback()
  : msg_(::rosidl_runtime_cpp::MessageInitialization::SKIP)
  {}
  ::interfaces::action::MoveTime_Feedback feedback(::interfaces::action::MoveTime_Feedback::_feedback_type arg)
  {
    msg_.feedback = std::move(arg);
    return std::move(msg_);
  }

private:
  ::interfaces::action::MoveTime_Feedback msg_;
};

}  // namespace builder

}  // namespace action

template<typename MessageType>
auto build();

template<>
inline
auto build<::interfaces::action::MoveTime_Feedback>()
{
  return interfaces::action::builder::Init_MoveTime_Feedback_feedback();
}

}  // namespace interfaces


namespace interfaces
{

namespace action
{

namespace builder
{

class Init_MoveTime_SendGoal_Request_goal
{
public:
  explicit Init_MoveTime_SendGoal_Request_goal(::interfaces::action::MoveTime_SendGoal_Request & msg)
  : msg_(msg)
  {}
  ::interfaces::action::MoveTime_SendGoal_Request goal(::interfaces::action::MoveTime_SendGoal_Request::_goal_type arg)
  {
    msg_.goal = std::move(arg);
    return std::move(msg_);
  }

private:
  ::interfaces::action::MoveTime_SendGoal_Request msg_;
};

class Init_MoveTime_SendGoal_Request_goal_id
{
public:
  Init_MoveTime_SendGoal_Request_goal_id()
  : msg_(::rosidl_runtime_cpp::MessageInitialization::SKIP)
  {}
  Init_MoveTime_SendGoal_Request_goal goal_id(::interfaces::action::MoveTime_SendGoal_Request::_goal_id_type arg)
  {
    msg_.goal_id = std::move(arg);
    return Init_MoveTime_SendGoal_Request_goal(msg_);
  }

private:
  ::interfaces::action::MoveTime_SendGoal_Request msg_;
};

}  // namespace builder

}  // namespace action

template<typename MessageType>
auto build();

template<>
inline
auto build<::interfaces::action::MoveTime_SendGoal_Request>()
{
  return interfaces::action::builder::Init_MoveTime_SendGoal_Request_goal_id();
}

}  // namespace interfaces


namespace interfaces
{

namespace action
{

namespace builder
{

class Init_MoveTime_SendGoal_Response_stamp
{
public:
  explicit Init_MoveTime_SendGoal_Response_stamp(::interfaces::action::MoveTime_SendGoal_Response & msg)
  : msg_(msg)
  {}
  ::interfaces::action::MoveTime_SendGoal_Response stamp(::interfaces::action::MoveTime_SendGoal_Response::_stamp_type arg)
  {
    msg_.stamp = std::move(arg);
    return std::move(msg_);
  }

private:
  ::interfaces::action::MoveTime_SendGoal_Response msg_;
};

class Init_MoveTime_SendGoal_Response_accepted
{
public:
  Init_MoveTime_SendGoal_Response_accepted()
  : msg_(::rosidl_runtime_cpp::MessageInitialization::SKIP)
  {}
  Init_MoveTime_SendGoal_Response_stamp accepted(::interfaces::action::MoveTime_SendGoal_Response::_accepted_type arg)
  {
    msg_.accepted = std::move(arg);
    return Init_MoveTime_SendGoal_Response_stamp(msg_);
  }

private:
  ::interfaces::action::MoveTime_SendGoal_Response msg_;
};

}  // namespace builder

}  // namespace action

template<typename MessageType>
auto build();

template<>
inline
auto build<::interfaces::action::MoveTime_SendGoal_Response>()
{
  return interfaces::action::builder::Init_MoveTime_SendGoal_Response_accepted();
}

}  // namespace interfaces


namespace interfaces
{

namespace action
{

namespace builder
{

class Init_MoveTime_GetResult_Request_goal_id
{
public:
  Init_MoveTime_GetResult_Request_goal_id()
  : msg_(::rosidl_runtime_cpp::MessageInitialization::SKIP)
  {}
  ::interfaces::action::MoveTime_GetResult_Request goal_id(::interfaces::action::MoveTime_GetResult_Request::_goal_id_type arg)
  {
    msg_.goal_id = std::move(arg);
    return std::move(msg_);
  }

private:
  ::interfaces::action::MoveTime_GetResult_Request msg_;
};

}  // namespace builder

}  // namespace action

template<typename MessageType>
auto build();

template<>
inline
auto build<::interfaces::action::MoveTime_GetResult_Request>()
{
  return interfaces::action::builder::Init_MoveTime_GetResult_Request_goal_id();
}

}  // namespace interfaces


namespace interfaces
{

namespace action
{

namespace builder
{

class Init_MoveTime_GetResult_Response_result
{
public:
  explicit Init_MoveTime_GetResult_Response_result(::interfaces::action::MoveTime_GetResult_Response & msg)
  : msg_(msg)
  {}
  ::interfaces::action::MoveTime_GetResult_Response result(::interfaces::action::MoveTime_GetResult_Response::_result_type arg)
  {
    msg_.result = std::move(arg);
    return std::move(msg_);
  }

private:
  ::interfaces::action::MoveTime_GetResult_Response msg_;
};

class Init_MoveTime_GetResult_Response_status
{
public:
  Init_MoveTime_GetResult_Response_status()
  : msg_(::rosidl_runtime_cpp::MessageInitialization::SKIP)
  {}
  Init_MoveTime_GetResult_Response_result status(::interfaces::action::MoveTime_GetResult_Response::_status_type arg)
  {
    msg_.status = std::move(arg);
    return Init_MoveTime_GetResult_Response_result(msg_);
  }

private:
  ::interfaces::action::MoveTime_GetResult_Response msg_;
};

}  // namespace builder

}  // namespace action

template<typename MessageType>
auto build();

template<>
inline
auto build<::interfaces::action::MoveTime_GetResult_Response>()
{
  return interfaces::action::builder::Init_MoveTime_GetResult_Response_status();
}

}  // namespace interfaces


namespace interfaces
{

namespace action
{

namespace builder
{

class Init_MoveTime_FeedbackMessage_feedback
{
public:
  explicit Init_MoveTime_FeedbackMessage_feedback(::interfaces::action::MoveTime_FeedbackMessage & msg)
  : msg_(msg)
  {}
  ::interfaces::action::MoveTime_FeedbackMessage feedback(::interfaces::action::MoveTime_FeedbackMessage::_feedback_type arg)
  {
    msg_.feedback = std::move(arg);
    return std::move(msg_);
  }

private:
  ::interfaces::action::MoveTime_FeedbackMessage msg_;
};

class Init_MoveTime_FeedbackMessage_goal_id
{
public:
  Init_MoveTime_FeedbackMessage_goal_id()
  : msg_(::rosidl_runtime_cpp::MessageInitialization::SKIP)
  {}
  Init_MoveTime_FeedbackMessage_feedback goal_id(::interfaces::action::MoveTime_FeedbackMessage::_goal_id_type arg)
  {
    msg_.goal_id = std::move(arg);
    return Init_MoveTime_FeedbackMessage_feedback(msg_);
  }

private:
  ::interfaces::action::MoveTime_FeedbackMessage msg_;
};

}  // namespace builder

}  // namespace action

template<typename MessageType>
auto build();

template<>
inline
auto build<::interfaces::action::MoveTime_FeedbackMessage>()
{
  return interfaces::action::builder::Init_MoveTime_FeedbackMessage_goal_id();
}

}  // namespace interfaces

#endif  // INTERFACES__ACTION__DETAIL__MOVE_TIME__BUILDER_HPP_
