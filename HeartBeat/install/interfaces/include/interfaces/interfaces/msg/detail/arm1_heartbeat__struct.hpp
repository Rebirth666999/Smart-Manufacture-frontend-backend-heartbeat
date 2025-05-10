// generated from rosidl_generator_cpp/resource/idl__struct.hpp.em
// with input from interfaces:msg/Arm1Heartbeat.idl
// generated code does not contain a copyright notice

#ifndef INTERFACES__MSG__DETAIL__ARM1_HEARTBEAT__STRUCT_HPP_
#define INTERFACES__MSG__DETAIL__ARM1_HEARTBEAT__STRUCT_HPP_

#include <algorithm>
#include <array>
#include <memory>
#include <string>
#include <vector>

#include "rosidl_runtime_cpp/bounded_vector.hpp"
#include "rosidl_runtime_cpp/message_initialization.hpp"


#ifndef _WIN32
# define DEPRECATED__interfaces__msg__Arm1Heartbeat __attribute__((deprecated))
#else
# define DEPRECATED__interfaces__msg__Arm1Heartbeat __declspec(deprecated)
#endif

namespace interfaces
{

namespace msg
{

// message struct
template<class ContainerAllocator>
struct Arm1Heartbeat_
{
  using Type = Arm1Heartbeat_<ContainerAllocator>;

  explicit Arm1Heartbeat_(rosidl_runtime_cpp::MessageInitialization _init = rosidl_runtime_cpp::MessageInitialization::ALL)
  {
    if (rosidl_runtime_cpp::MessageInitialization::ALL == _init ||
      rosidl_runtime_cpp::MessageInitialization::ZERO == _init)
    {
      this->number = 0l;
    }
  }

  explicit Arm1Heartbeat_(const ContainerAllocator & _alloc, rosidl_runtime_cpp::MessageInitialization _init = rosidl_runtime_cpp::MessageInitialization::ALL)
  {
    (void)_alloc;
    if (rosidl_runtime_cpp::MessageInitialization::ALL == _init ||
      rosidl_runtime_cpp::MessageInitialization::ZERO == _init)
    {
      this->number = 0l;
    }
  }

  // field types and members
  using _number_type =
    int32_t;
  _number_type number;

  // setters for named parameter idiom
  Type & set__number(
    const int32_t & _arg)
  {
    this->number = _arg;
    return *this;
  }

  // constant declarations

  // pointer types
  using RawPtr =
    interfaces::msg::Arm1Heartbeat_<ContainerAllocator> *;
  using ConstRawPtr =
    const interfaces::msg::Arm1Heartbeat_<ContainerAllocator> *;
  using SharedPtr =
    std::shared_ptr<interfaces::msg::Arm1Heartbeat_<ContainerAllocator>>;
  using ConstSharedPtr =
    std::shared_ptr<interfaces::msg::Arm1Heartbeat_<ContainerAllocator> const>;

  template<typename Deleter = std::default_delete<
      interfaces::msg::Arm1Heartbeat_<ContainerAllocator>>>
  using UniquePtrWithDeleter =
    std::unique_ptr<interfaces::msg::Arm1Heartbeat_<ContainerAllocator>, Deleter>;

  using UniquePtr = UniquePtrWithDeleter<>;

  template<typename Deleter = std::default_delete<
      interfaces::msg::Arm1Heartbeat_<ContainerAllocator>>>
  using ConstUniquePtrWithDeleter =
    std::unique_ptr<interfaces::msg::Arm1Heartbeat_<ContainerAllocator> const, Deleter>;
  using ConstUniquePtr = ConstUniquePtrWithDeleter<>;

  using WeakPtr =
    std::weak_ptr<interfaces::msg::Arm1Heartbeat_<ContainerAllocator>>;
  using ConstWeakPtr =
    std::weak_ptr<interfaces::msg::Arm1Heartbeat_<ContainerAllocator> const>;

  // pointer types similar to ROS 1, use SharedPtr / ConstSharedPtr instead
  // NOTE: Can't use 'using' here because GNU C++ can't parse attributes properly
  typedef DEPRECATED__interfaces__msg__Arm1Heartbeat
    std::shared_ptr<interfaces::msg::Arm1Heartbeat_<ContainerAllocator>>
    Ptr;
  typedef DEPRECATED__interfaces__msg__Arm1Heartbeat
    std::shared_ptr<interfaces::msg::Arm1Heartbeat_<ContainerAllocator> const>
    ConstPtr;

  // comparison operators
  bool operator==(const Arm1Heartbeat_ & other) const
  {
    if (this->number != other.number) {
      return false;
    }
    return true;
  }
  bool operator!=(const Arm1Heartbeat_ & other) const
  {
    return !this->operator==(other);
  }
};  // struct Arm1Heartbeat_

// alias to use template instance with default allocator
using Arm1Heartbeat =
  interfaces::msg::Arm1Heartbeat_<std::allocator<void>>;

// constant definitions

}  // namespace msg

}  // namespace interfaces

#endif  // INTERFACES__MSG__DETAIL__ARM1_HEARTBEAT__STRUCT_HPP_
