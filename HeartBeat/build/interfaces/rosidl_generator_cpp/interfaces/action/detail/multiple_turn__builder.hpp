// generated from rosidl_generator_cpp/resource/idl__builder.hpp.em
// with input from interfaces:action/MultipleTurn.idl
// generated code does not contain a copyright notice

#ifndef INTERFACES__ACTION__DETAIL__MULTIPLE_TURN__BUILDER_HPP_
#define INTERFACES__ACTION__DETAIL__MULTIPLE_TURN__BUILDER_HPP_

#include <algorithm>
#include <utility>

#include "interfaces/action/detail/multiple_turn__struct.hpp"
#include "rosidl_runtime_cpp/message_initialization.hpp"


namespace interfaces
{

namespace action
{

namespace builder
{

class Init_MultipleTurn_Goal_lst
{
public:
  explicit Init_MultipleTurn_Goal_lst(::interfaces::action::MultipleTurn_Goal & msg)
  : msg_(msg)
  {}
  ::interfaces::action::MultipleTurn_Goal lst(::interfaces::action::MultipleTurn_Goal::_lst_type arg)
  {
    msg_.lst = std::move(arg);
    return std::move(msg_);
  }

private:
  ::interfaces::action::MultipleTurn_Goal msg_;
};

class Init_MultipleTurn_Goal_angles
{
public:
  explicit Init_MultipleTurn_Goal_angles(::interfaces::action::MultipleTurn_Goal & msg)
  : msg_(msg)
  {}
  Init_MultipleTurn_Goal_lst angles(::interfaces::action::MultipleTurn_Goal::_angles_type arg)
  {
    msg_.angles = std::move(arg);
    return Init_MultipleTurn_Goal_lst(msg_);
  }

private:
  ::interfaces::action::MultipleTurn_Goal msg_;
};

class Init_MultipleTurn_Goal_joint_ids
{
public:
  Init_MultipleTurn_Goal_joint_ids()
  : msg_(::rosidl_runtime_cpp::MessageInitialization::SKIP)
  {}
  Init_MultipleTurn_Goal_angles joint_ids(::interfaces::action::MultipleTurn_Goal::_joint_ids_type arg)
  {
    msg_.joint_ids = std::move(arg);
    return Init_MultipleTurn_Goal_angles(msg_);
  }

private:
  ::interfaces::action::MultipleTurn_Goal msg_;
};

}  // namespace builder

}  // namespace action

template<typename MessageType>
auto build();

template<>
inline
auto build<::interfaces::action::MultipleTurn_Goal>()
{
  return interfaces::action::builder::Init_MultipleTurn_Goal_joint_ids();
}

}  // namespace interfaces


namespace interfaces
{

namespace action
{

namespace builder
{

class Init_MultipleTurn_Result_finish
{
public:
  Init_MultipleTurn_Result_finish()
  : msg_(::rosidl_runtime_cpp::MessageInitialization::SKIP)
  {}
  ::interfaces::action::MultipleTurn_Result finish(::interfaces::action::MultipleTurn_Result::_finish_type arg)
  {
    msg_.finish = std::move(arg);
    return std::move(msg_);
  }

private:
  ::interfaces::action::MultipleTurn_Result msg_;
};

}  // namespace builder

}  // namespace action

template<typename MessageType>
auto build();

template<>
inline
auto build<::interfaces::action::MultipleTurn_Result>()
{
  return interfaces::action::builder::Init_MultipleTurn_Result_finish();
}

}  // namespace interfaces


namespace interfaces
{

namespace action
{

namespace builder
{

class Init_MultipleTurn_Feedback_timespan
{
public:
  explicit Init_MultipleTurn_Feedback_timespan(::interfaces::action::MultipleTurn_Feedback & msg)
  : msg_(msg)
  {}
  ::interfaces::action::MultipleTurn_Feedback timespan(::interfaces::action::MultipleTurn_Feedback::_timespan_type arg)
  {
    msg_.timespan = std::move(arg);
    return std::move(msg_);
  }

private:
  ::interfaces::action::MultipleTurn_Feedback msg_;
};

class Init_MultipleTurn_Feedback_ast
{
public:
  Init_MultipleTurn_Feedback_ast()
  : msg_(::rosidl_runtime_cpp::MessageInitialization::SKIP)
  {}
  Init_MultipleTurn_Feedback_timespan ast(::interfaces::action::MultipleTurn_Feedback::_ast_type arg)
  {
    msg_.ast = std::move(arg);
    return Init_MultipleTurn_Feedback_timespan(msg_);
  }

private:
  ::interfaces::action::MultipleTurn_Feedback msg_;
};

}  // namespace builder

}  // namespace action

template<typename MessageType>
auto build();

template<>
inline
auto build<::interfaces::action::MultipleTurn_Feedback>()
{
  return interfaces::action::builder::Init_MultipleTurn_Feedback_ast();
}

}  // namespace interfaces


namespace interfaces
{

namespace action
{

namespace builder
{

class Init_MultipleTurn_SendGoal_Request_goal
{
public:
  explicit Init_MultipleTurn_SendGoal_Request_goal(::interfaces::action::MultipleTurn_SendGoal_Request & msg)
  : msg_(msg)
  {}
  ::interfaces::action::MultipleTurn_SendGoal_Request goal(::interfaces::action::MultipleTurn_SendGoal_Request::_goal_type arg)
  {
    msg_.goal = std::move(arg);
    return std::move(msg_);
  }

private:
  ::interfaces::action::MultipleTurn_SendGoal_Request msg_;
};

class Init_MultipleTurn_SendGoal_Request_goal_id
{
public:
  Init_MultipleTurn_SendGoal_Request_goal_id()
  : msg_(::rosidl_runtime_cpp::MessageInitialization::SKIP)
  {}
  Init_MultipleTurn_SendGoal_Request_goal goal_id(::interfaces::action::MultipleTurn_SendGoal_Request::_goal_id_type arg)
  {
    msg_.goal_id = std::move(arg);
    return Init_MultipleTurn_SendGoal_Request_goal(msg_);
  }

private:
  ::interfaces::action::MultipleTurn_SendGoal_Request msg_;
};

}  // namespace builder

}  // namespace action

template<typename MessageType>
auto build();

template<>
inline
auto build<::interfaces::action::MultipleTurn_SendGoal_Request>()
{
  return interfaces::action::builder::Init_MultipleTurn_SendGoal_Request_goal_id();
}

}  // namespace interfaces


namespace interfaces
{

namespace action
{

namespace builder
{

class Init_MultipleTurn_SendGoal_Response_stamp
{
public:
  explicit Init_MultipleTurn_SendGoal_Response_stamp(::interfaces::action::MultipleTurn_SendGoal_Response & msg)
  : msg_(msg)
  {}
  ::interfaces::action::MultipleTurn_SendGoal_Response stamp(::interfaces::action::MultipleTurn_SendGoal_Response::_stamp_type arg)
  {
    msg_.stamp = std::move(arg);
    return std::move(msg_);
  }

private:
  ::interfaces::action::MultipleTurn_SendGoal_Response msg_;
};

class Init_MultipleTurn_SendGoal_Response_accepted
{
public:
  Init_MultipleTurn_SendGoal_Response_accepted()
  : msg_(::rosidl_runtime_cpp::MessageInitialization::SKIP)
  {}
  Init_MultipleTurn_SendGoal_Response_stamp accepted(::interfaces::action::MultipleTurn_SendGoal_Response::_accepted_type arg)
  {
    msg_.accepted = std::move(arg);
    return Init_MultipleTurn_SendGoal_Response_stamp(msg_);
  }

private:
  ::interfaces::action::MultipleTurn_SendGoal_Response msg_;
};

}  // namespace builder

}  // namespace action

template<typename MessageType>
auto build();

template<>
inline
auto build<::interfaces::action::MultipleTurn_SendGoal_Response>()
{
  return interfaces::action::builder::Init_MultipleTurn_SendGoal_Response_accepted();
}

}  // namespace interfaces


namespace interfaces
{

namespace action
{

namespace builder
{

class Init_MultipleTurn_GetResult_Request_goal_id
{
public:
  Init_MultipleTurn_GetResult_Request_goal_id()
  : msg_(::rosidl_runtime_cpp::MessageInitialization::SKIP)
  {}
  ::interfaces::action::MultipleTurn_GetResult_Request goal_id(::interfaces::action::MultipleTurn_GetResult_Request::_goal_id_type arg)
  {
    msg_.goal_id = std::move(arg);
    return std::move(msg_);
  }

private:
  ::interfaces::action::MultipleTurn_GetResult_Request msg_;
};

}  // namespace builder

}  // namespace action

template<typename MessageType>
auto build();

template<>
inline
auto build<::interfaces::action::MultipleTurn_GetResult_Request>()
{
  return interfaces::action::builder::Init_MultipleTurn_GetResult_Request_goal_id();
}

}  // namespace interfaces


namespace interfaces
{

namespace action
{

namespace builder
{

class Init_MultipleTurn_GetResult_Response_result
{
public:
  explicit Init_MultipleTurn_GetResult_Response_result(::interfaces::action::MultipleTurn_GetResult_Response & msg)
  : msg_(msg)
  {}
  ::interfaces::action::MultipleTurn_GetResult_Response result(::interfaces::action::MultipleTurn_GetResult_Response::_result_type arg)
  {
    msg_.result = std::move(arg);
    return std::move(msg_);
  }

private:
  ::interfaces::action::MultipleTurn_GetResult_Response msg_;
};

class Init_MultipleTurn_GetResult_Response_status
{
public:
  Init_MultipleTurn_GetResult_Response_status()
  : msg_(::rosidl_runtime_cpp::MessageInitialization::SKIP)
  {}
  Init_MultipleTurn_GetResult_Response_result status(::interfaces::action::MultipleTurn_GetResult_Response::_status_type arg)
  {
    msg_.status = std::move(arg);
    return Init_MultipleTurn_GetResult_Response_result(msg_);
  }

private:
  ::interfaces::action::MultipleTurn_GetResult_Response msg_;
};

}  // namespace builder

}  // namespace action

template<typename MessageType>
auto build();

template<>
inline
auto build<::interfaces::action::MultipleTurn_GetResult_Response>()
{
  return interfaces::action::builder::Init_MultipleTurn_GetResult_Response_status();
}

}  // namespace interfaces


namespace interfaces
{

namespace action
{

namespace builder
{

class Init_MultipleTurn_FeedbackMessage_feedback
{
public:
  explicit Init_MultipleTurn_FeedbackMessage_feedback(::interfaces::action::MultipleTurn_FeedbackMessage & msg)
  : msg_(msg)
  {}
  ::interfaces::action::MultipleTurn_FeedbackMessage feedback(::interfaces::action::MultipleTurn_FeedbackMessage::_feedback_type arg)
  {
    msg_.feedback = std::move(arg);
    return std::move(msg_);
  }

private:
  ::interfaces::action::MultipleTurn_FeedbackMessage msg_;
};

class Init_MultipleTurn_FeedbackMessage_goal_id
{
public:
  Init_MultipleTurn_FeedbackMessage_goal_id()
  : msg_(::rosidl_runtime_cpp::MessageInitialization::SKIP)
  {}
  Init_MultipleTurn_FeedbackMessage_feedback goal_id(::interfaces::action::MultipleTurn_FeedbackMessage::_goal_id_type arg)
  {
    msg_.goal_id = std::move(arg);
    return Init_MultipleTurn_FeedbackMessage_feedback(msg_);
  }

private:
  ::interfaces::action::MultipleTurn_FeedbackMessage msg_;
};

}  // namespace builder

}  // namespace action

template<typename MessageType>
auto build();

template<>
inline
auto build<::interfaces::action::MultipleTurn_FeedbackMessage>()
{
  return interfaces::action::builder::Init_MultipleTurn_FeedbackMessage_goal_id();
}

}  // namespace interfaces

#endif  // INTERFACES__ACTION__DETAIL__MULTIPLE_TURN__BUILDER_HPP_
