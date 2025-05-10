// generated from rosidl_generator_cpp/resource/idl__builder.hpp.em
// with input from interfaces:action/GetStatus.idl
// generated code does not contain a copyright notice

#ifndef INTERFACES__ACTION__DETAIL__GET_STATUS__BUILDER_HPP_
#define INTERFACES__ACTION__DETAIL__GET_STATUS__BUILDER_HPP_

#include <algorithm>
#include <utility>

#include "interfaces/action/detail/get_status__struct.hpp"
#include "rosidl_runtime_cpp/message_initialization.hpp"


namespace interfaces
{

namespace action
{


}  // namespace action

template<typename MessageType>
auto build();

template<>
inline
auto build<::interfaces::action::GetStatus_Goal>()
{
  return ::interfaces::action::GetStatus_Goal(rosidl_runtime_cpp::MessageInitialization::ZERO);
}

}  // namespace interfaces


namespace interfaces
{

namespace action
{

namespace builder
{

class Init_GetStatus_Result_finish
{
public:
  Init_GetStatus_Result_finish()
  : msg_(::rosidl_runtime_cpp::MessageInitialization::SKIP)
  {}
  ::interfaces::action::GetStatus_Result finish(::interfaces::action::GetStatus_Result::_finish_type arg)
  {
    msg_.finish = std::move(arg);
    return std::move(msg_);
  }

private:
  ::interfaces::action::GetStatus_Result msg_;
};

}  // namespace builder

}  // namespace action

template<typename MessageType>
auto build();

template<>
inline
auto build<::interfaces::action::GetStatus_Result>()
{
  return interfaces::action::builder::Init_GetStatus_Result_finish();
}

}  // namespace interfaces


namespace interfaces
{

namespace action
{

namespace builder
{

class Init_GetStatus_Feedback_feedback
{
public:
  Init_GetStatus_Feedback_feedback()
  : msg_(::rosidl_runtime_cpp::MessageInitialization::SKIP)
  {}
  ::interfaces::action::GetStatus_Feedback feedback(::interfaces::action::GetStatus_Feedback::_feedback_type arg)
  {
    msg_.feedback = std::move(arg);
    return std::move(msg_);
  }

private:
  ::interfaces::action::GetStatus_Feedback msg_;
};

}  // namespace builder

}  // namespace action

template<typename MessageType>
auto build();

template<>
inline
auto build<::interfaces::action::GetStatus_Feedback>()
{
  return interfaces::action::builder::Init_GetStatus_Feedback_feedback();
}

}  // namespace interfaces


namespace interfaces
{

namespace action
{

namespace builder
{

class Init_GetStatus_SendGoal_Request_goal
{
public:
  explicit Init_GetStatus_SendGoal_Request_goal(::interfaces::action::GetStatus_SendGoal_Request & msg)
  : msg_(msg)
  {}
  ::interfaces::action::GetStatus_SendGoal_Request goal(::interfaces::action::GetStatus_SendGoal_Request::_goal_type arg)
  {
    msg_.goal = std::move(arg);
    return std::move(msg_);
  }

private:
  ::interfaces::action::GetStatus_SendGoal_Request msg_;
};

class Init_GetStatus_SendGoal_Request_goal_id
{
public:
  Init_GetStatus_SendGoal_Request_goal_id()
  : msg_(::rosidl_runtime_cpp::MessageInitialization::SKIP)
  {}
  Init_GetStatus_SendGoal_Request_goal goal_id(::interfaces::action::GetStatus_SendGoal_Request::_goal_id_type arg)
  {
    msg_.goal_id = std::move(arg);
    return Init_GetStatus_SendGoal_Request_goal(msg_);
  }

private:
  ::interfaces::action::GetStatus_SendGoal_Request msg_;
};

}  // namespace builder

}  // namespace action

template<typename MessageType>
auto build();

template<>
inline
auto build<::interfaces::action::GetStatus_SendGoal_Request>()
{
  return interfaces::action::builder::Init_GetStatus_SendGoal_Request_goal_id();
}

}  // namespace interfaces


namespace interfaces
{

namespace action
{

namespace builder
{

class Init_GetStatus_SendGoal_Response_stamp
{
public:
  explicit Init_GetStatus_SendGoal_Response_stamp(::interfaces::action::GetStatus_SendGoal_Response & msg)
  : msg_(msg)
  {}
  ::interfaces::action::GetStatus_SendGoal_Response stamp(::interfaces::action::GetStatus_SendGoal_Response::_stamp_type arg)
  {
    msg_.stamp = std::move(arg);
    return std::move(msg_);
  }

private:
  ::interfaces::action::GetStatus_SendGoal_Response msg_;
};

class Init_GetStatus_SendGoal_Response_accepted
{
public:
  Init_GetStatus_SendGoal_Response_accepted()
  : msg_(::rosidl_runtime_cpp::MessageInitialization::SKIP)
  {}
  Init_GetStatus_SendGoal_Response_stamp accepted(::interfaces::action::GetStatus_SendGoal_Response::_accepted_type arg)
  {
    msg_.accepted = std::move(arg);
    return Init_GetStatus_SendGoal_Response_stamp(msg_);
  }

private:
  ::interfaces::action::GetStatus_SendGoal_Response msg_;
};

}  // namespace builder

}  // namespace action

template<typename MessageType>
auto build();

template<>
inline
auto build<::interfaces::action::GetStatus_SendGoal_Response>()
{
  return interfaces::action::builder::Init_GetStatus_SendGoal_Response_accepted();
}

}  // namespace interfaces


namespace interfaces
{

namespace action
{

namespace builder
{

class Init_GetStatus_GetResult_Request_goal_id
{
public:
  Init_GetStatus_GetResult_Request_goal_id()
  : msg_(::rosidl_runtime_cpp::MessageInitialization::SKIP)
  {}
  ::interfaces::action::GetStatus_GetResult_Request goal_id(::interfaces::action::GetStatus_GetResult_Request::_goal_id_type arg)
  {
    msg_.goal_id = std::move(arg);
    return std::move(msg_);
  }

private:
  ::interfaces::action::GetStatus_GetResult_Request msg_;
};

}  // namespace builder

}  // namespace action

template<typename MessageType>
auto build();

template<>
inline
auto build<::interfaces::action::GetStatus_GetResult_Request>()
{
  return interfaces::action::builder::Init_GetStatus_GetResult_Request_goal_id();
}

}  // namespace interfaces


namespace interfaces
{

namespace action
{

namespace builder
{

class Init_GetStatus_GetResult_Response_result
{
public:
  explicit Init_GetStatus_GetResult_Response_result(::interfaces::action::GetStatus_GetResult_Response & msg)
  : msg_(msg)
  {}
  ::interfaces::action::GetStatus_GetResult_Response result(::interfaces::action::GetStatus_GetResult_Response::_result_type arg)
  {
    msg_.result = std::move(arg);
    return std::move(msg_);
  }

private:
  ::interfaces::action::GetStatus_GetResult_Response msg_;
};

class Init_GetStatus_GetResult_Response_status
{
public:
  Init_GetStatus_GetResult_Response_status()
  : msg_(::rosidl_runtime_cpp::MessageInitialization::SKIP)
  {}
  Init_GetStatus_GetResult_Response_result status(::interfaces::action::GetStatus_GetResult_Response::_status_type arg)
  {
    msg_.status = std::move(arg);
    return Init_GetStatus_GetResult_Response_result(msg_);
  }

private:
  ::interfaces::action::GetStatus_GetResult_Response msg_;
};

}  // namespace builder

}  // namespace action

template<typename MessageType>
auto build();

template<>
inline
auto build<::interfaces::action::GetStatus_GetResult_Response>()
{
  return interfaces::action::builder::Init_GetStatus_GetResult_Response_status();
}

}  // namespace interfaces


namespace interfaces
{

namespace action
{

namespace builder
{

class Init_GetStatus_FeedbackMessage_feedback
{
public:
  explicit Init_GetStatus_FeedbackMessage_feedback(::interfaces::action::GetStatus_FeedbackMessage & msg)
  : msg_(msg)
  {}
  ::interfaces::action::GetStatus_FeedbackMessage feedback(::interfaces::action::GetStatus_FeedbackMessage::_feedback_type arg)
  {
    msg_.feedback = std::move(arg);
    return std::move(msg_);
  }

private:
  ::interfaces::action::GetStatus_FeedbackMessage msg_;
};

class Init_GetStatus_FeedbackMessage_goal_id
{
public:
  Init_GetStatus_FeedbackMessage_goal_id()
  : msg_(::rosidl_runtime_cpp::MessageInitialization::SKIP)
  {}
  Init_GetStatus_FeedbackMessage_feedback goal_id(::interfaces::action::GetStatus_FeedbackMessage::_goal_id_type arg)
  {
    msg_.goal_id = std::move(arg);
    return Init_GetStatus_FeedbackMessage_feedback(msg_);
  }

private:
  ::interfaces::action::GetStatus_FeedbackMessage msg_;
};

}  // namespace builder

}  // namespace action

template<typename MessageType>
auto build();

template<>
inline
auto build<::interfaces::action::GetStatus_FeedbackMessage>()
{
  return interfaces::action::builder::Init_GetStatus_FeedbackMessage_goal_id();
}

}  // namespace interfaces

#endif  // INTERFACES__ACTION__DETAIL__GET_STATUS__BUILDER_HPP_
