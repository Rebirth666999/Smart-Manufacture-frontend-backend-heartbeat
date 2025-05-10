// generated from rosidl_generator_cpp/resource/idl__builder.hpp.em
// with input from interfaces:action/BeltMove.idl
// generated code does not contain a copyright notice

#ifndef INTERFACES__ACTION__DETAIL__BELT_MOVE__BUILDER_HPP_
#define INTERFACES__ACTION__DETAIL__BELT_MOVE__BUILDER_HPP_

#include <algorithm>
#include <utility>

#include "interfaces/action/detail/belt_move__struct.hpp"
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
auto build<::interfaces::action::BeltMove_Goal>()
{
  return ::interfaces::action::BeltMove_Goal(rosidl_runtime_cpp::MessageInitialization::ZERO);
}

}  // namespace interfaces


namespace interfaces
{

namespace action
{

namespace builder
{

class Init_BeltMove_Result_finish
{
public:
  Init_BeltMove_Result_finish()
  : msg_(::rosidl_runtime_cpp::MessageInitialization::SKIP)
  {}
  ::interfaces::action::BeltMove_Result finish(::interfaces::action::BeltMove_Result::_finish_type arg)
  {
    msg_.finish = std::move(arg);
    return std::move(msg_);
  }

private:
  ::interfaces::action::BeltMove_Result msg_;
};

}  // namespace builder

}  // namespace action

template<typename MessageType>
auto build();

template<>
inline
auto build<::interfaces::action::BeltMove_Result>()
{
  return interfaces::action::builder::Init_BeltMove_Result_finish();
}

}  // namespace interfaces


namespace interfaces
{

namespace action
{

namespace builder
{

class Init_BeltMove_Feedback_feedback
{
public:
  Init_BeltMove_Feedback_feedback()
  : msg_(::rosidl_runtime_cpp::MessageInitialization::SKIP)
  {}
  ::interfaces::action::BeltMove_Feedback feedback(::interfaces::action::BeltMove_Feedback::_feedback_type arg)
  {
    msg_.feedback = std::move(arg);
    return std::move(msg_);
  }

private:
  ::interfaces::action::BeltMove_Feedback msg_;
};

}  // namespace builder

}  // namespace action

template<typename MessageType>
auto build();

template<>
inline
auto build<::interfaces::action::BeltMove_Feedback>()
{
  return interfaces::action::builder::Init_BeltMove_Feedback_feedback();
}

}  // namespace interfaces


namespace interfaces
{

namespace action
{

namespace builder
{

class Init_BeltMove_SendGoal_Request_goal
{
public:
  explicit Init_BeltMove_SendGoal_Request_goal(::interfaces::action::BeltMove_SendGoal_Request & msg)
  : msg_(msg)
  {}
  ::interfaces::action::BeltMove_SendGoal_Request goal(::interfaces::action::BeltMove_SendGoal_Request::_goal_type arg)
  {
    msg_.goal = std::move(arg);
    return std::move(msg_);
  }

private:
  ::interfaces::action::BeltMove_SendGoal_Request msg_;
};

class Init_BeltMove_SendGoal_Request_goal_id
{
public:
  Init_BeltMove_SendGoal_Request_goal_id()
  : msg_(::rosidl_runtime_cpp::MessageInitialization::SKIP)
  {}
  Init_BeltMove_SendGoal_Request_goal goal_id(::interfaces::action::BeltMove_SendGoal_Request::_goal_id_type arg)
  {
    msg_.goal_id = std::move(arg);
    return Init_BeltMove_SendGoal_Request_goal(msg_);
  }

private:
  ::interfaces::action::BeltMove_SendGoal_Request msg_;
};

}  // namespace builder

}  // namespace action

template<typename MessageType>
auto build();

template<>
inline
auto build<::interfaces::action::BeltMove_SendGoal_Request>()
{
  return interfaces::action::builder::Init_BeltMove_SendGoal_Request_goal_id();
}

}  // namespace interfaces


namespace interfaces
{

namespace action
{

namespace builder
{

class Init_BeltMove_SendGoal_Response_stamp
{
public:
  explicit Init_BeltMove_SendGoal_Response_stamp(::interfaces::action::BeltMove_SendGoal_Response & msg)
  : msg_(msg)
  {}
  ::interfaces::action::BeltMove_SendGoal_Response stamp(::interfaces::action::BeltMove_SendGoal_Response::_stamp_type arg)
  {
    msg_.stamp = std::move(arg);
    return std::move(msg_);
  }

private:
  ::interfaces::action::BeltMove_SendGoal_Response msg_;
};

class Init_BeltMove_SendGoal_Response_accepted
{
public:
  Init_BeltMove_SendGoal_Response_accepted()
  : msg_(::rosidl_runtime_cpp::MessageInitialization::SKIP)
  {}
  Init_BeltMove_SendGoal_Response_stamp accepted(::interfaces::action::BeltMove_SendGoal_Response::_accepted_type arg)
  {
    msg_.accepted = std::move(arg);
    return Init_BeltMove_SendGoal_Response_stamp(msg_);
  }

private:
  ::interfaces::action::BeltMove_SendGoal_Response msg_;
};

}  // namespace builder

}  // namespace action

template<typename MessageType>
auto build();

template<>
inline
auto build<::interfaces::action::BeltMove_SendGoal_Response>()
{
  return interfaces::action::builder::Init_BeltMove_SendGoal_Response_accepted();
}

}  // namespace interfaces


namespace interfaces
{

namespace action
{

namespace builder
{

class Init_BeltMove_GetResult_Request_goal_id
{
public:
  Init_BeltMove_GetResult_Request_goal_id()
  : msg_(::rosidl_runtime_cpp::MessageInitialization::SKIP)
  {}
  ::interfaces::action::BeltMove_GetResult_Request goal_id(::interfaces::action::BeltMove_GetResult_Request::_goal_id_type arg)
  {
    msg_.goal_id = std::move(arg);
    return std::move(msg_);
  }

private:
  ::interfaces::action::BeltMove_GetResult_Request msg_;
};

}  // namespace builder

}  // namespace action

template<typename MessageType>
auto build();

template<>
inline
auto build<::interfaces::action::BeltMove_GetResult_Request>()
{
  return interfaces::action::builder::Init_BeltMove_GetResult_Request_goal_id();
}

}  // namespace interfaces


namespace interfaces
{

namespace action
{

namespace builder
{

class Init_BeltMove_GetResult_Response_result
{
public:
  explicit Init_BeltMove_GetResult_Response_result(::interfaces::action::BeltMove_GetResult_Response & msg)
  : msg_(msg)
  {}
  ::interfaces::action::BeltMove_GetResult_Response result(::interfaces::action::BeltMove_GetResult_Response::_result_type arg)
  {
    msg_.result = std::move(arg);
    return std::move(msg_);
  }

private:
  ::interfaces::action::BeltMove_GetResult_Response msg_;
};

class Init_BeltMove_GetResult_Response_status
{
public:
  Init_BeltMove_GetResult_Response_status()
  : msg_(::rosidl_runtime_cpp::MessageInitialization::SKIP)
  {}
  Init_BeltMove_GetResult_Response_result status(::interfaces::action::BeltMove_GetResult_Response::_status_type arg)
  {
    msg_.status = std::move(arg);
    return Init_BeltMove_GetResult_Response_result(msg_);
  }

private:
  ::interfaces::action::BeltMove_GetResult_Response msg_;
};

}  // namespace builder

}  // namespace action

template<typename MessageType>
auto build();

template<>
inline
auto build<::interfaces::action::BeltMove_GetResult_Response>()
{
  return interfaces::action::builder::Init_BeltMove_GetResult_Response_status();
}

}  // namespace interfaces


namespace interfaces
{

namespace action
{

namespace builder
{

class Init_BeltMove_FeedbackMessage_feedback
{
public:
  explicit Init_BeltMove_FeedbackMessage_feedback(::interfaces::action::BeltMove_FeedbackMessage & msg)
  : msg_(msg)
  {}
  ::interfaces::action::BeltMove_FeedbackMessage feedback(::interfaces::action::BeltMove_FeedbackMessage::_feedback_type arg)
  {
    msg_.feedback = std::move(arg);
    return std::move(msg_);
  }

private:
  ::interfaces::action::BeltMove_FeedbackMessage msg_;
};

class Init_BeltMove_FeedbackMessage_goal_id
{
public:
  Init_BeltMove_FeedbackMessage_goal_id()
  : msg_(::rosidl_runtime_cpp::MessageInitialization::SKIP)
  {}
  Init_BeltMove_FeedbackMessage_feedback goal_id(::interfaces::action::BeltMove_FeedbackMessage::_goal_id_type arg)
  {
    msg_.goal_id = std::move(arg);
    return Init_BeltMove_FeedbackMessage_feedback(msg_);
  }

private:
  ::interfaces::action::BeltMove_FeedbackMessage msg_;
};

}  // namespace builder

}  // namespace action

template<typename MessageType>
auto build();

template<>
inline
auto build<::interfaces::action::BeltMove_FeedbackMessage>()
{
  return interfaces::action::builder::Init_BeltMove_FeedbackMessage_goal_id();
}

}  // namespace interfaces

#endif  // INTERFACES__ACTION__DETAIL__BELT_MOVE__BUILDER_HPP_
