// generated from rosidl_generator_cpp/resource/idl__builder.hpp.em
// with input from interfaces:msg/Arm1Heartbeat.idl
// generated code does not contain a copyright notice

#ifndef INTERFACES__MSG__DETAIL__ARM1_HEARTBEAT__BUILDER_HPP_
#define INTERFACES__MSG__DETAIL__ARM1_HEARTBEAT__BUILDER_HPP_

#include <algorithm>
#include <utility>

#include "interfaces/msg/detail/arm1_heartbeat__struct.hpp"
#include "rosidl_runtime_cpp/message_initialization.hpp"


namespace interfaces
{

namespace msg
{

namespace builder
{

class Init_Arm1Heartbeat_number
{
public:
  Init_Arm1Heartbeat_number()
  : msg_(::rosidl_runtime_cpp::MessageInitialization::SKIP)
  {}
  ::interfaces::msg::Arm1Heartbeat number(::interfaces::msg::Arm1Heartbeat::_number_type arg)
  {
    msg_.number = std::move(arg);
    return std::move(msg_);
  }

private:
  ::interfaces::msg::Arm1Heartbeat msg_;
};

}  // namespace builder

}  // namespace msg

template<typename MessageType>
auto build();

template<>
inline
auto build<::interfaces::msg::Arm1Heartbeat>()
{
  return interfaces::msg::builder::Init_Arm1Heartbeat_number();
}

}  // namespace interfaces

#endif  // INTERFACES__MSG__DETAIL__ARM1_HEARTBEAT__BUILDER_HPP_
