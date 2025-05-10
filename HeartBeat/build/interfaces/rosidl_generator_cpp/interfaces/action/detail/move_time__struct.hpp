// generated from rosidl_generator_cpp/resource/idl__struct.hpp.em
// with input from interfaces:action/MoveTime.idl
// generated code does not contain a copyright notice

#ifndef INTERFACES__ACTION__DETAIL__MOVE_TIME__STRUCT_HPP_
#define INTERFACES__ACTION__DETAIL__MOVE_TIME__STRUCT_HPP_

#include <algorithm>
#include <array>
#include <memory>
#include <string>
#include <vector>

#include "rosidl_runtime_cpp/bounded_vector.hpp"
#include "rosidl_runtime_cpp/message_initialization.hpp"


#ifndef _WIN32
# define DEPRECATED__interfaces__action__MoveTime_Goal __attribute__((deprecated))
#else
# define DEPRECATED__interfaces__action__MoveTime_Goal __declspec(deprecated)
#endif

namespace interfaces
{

namespace action
{

// message struct
template<class ContainerAllocator>
struct MoveTime_Goal_
{
  using Type = MoveTime_Goal_<ContainerAllocator>;

  explicit MoveTime_Goal_(rosidl_runtime_cpp::MessageInitialization _init = rosidl_runtime_cpp::MessageInitialization::ALL)
  {
    if (rosidl_runtime_cpp::MessageInitialization::ALL == _init ||
      rosidl_runtime_cpp::MessageInitialization::ZERO == _init)
    {
      this->runtime = 0.0;
    }
  }

  explicit MoveTime_Goal_(const ContainerAllocator & _alloc, rosidl_runtime_cpp::MessageInitialization _init = rosidl_runtime_cpp::MessageInitialization::ALL)
  {
    (void)_alloc;
    if (rosidl_runtime_cpp::MessageInitialization::ALL == _init ||
      rosidl_runtime_cpp::MessageInitialization::ZERO == _init)
    {
      this->runtime = 0.0;
    }
  }

  // field types and members
  using _runtime_type =
    double;
  _runtime_type runtime;

  // setters for named parameter idiom
  Type & set__runtime(
    const double & _arg)
  {
    this->runtime = _arg;
    return *this;
  }

  // constant declarations

  // pointer types
  using RawPtr =
    interfaces::action::MoveTime_Goal_<ContainerAllocator> *;
  using ConstRawPtr =
    const interfaces::action::MoveTime_Goal_<ContainerAllocator> *;
  using SharedPtr =
    std::shared_ptr<interfaces::action::MoveTime_Goal_<ContainerAllocator>>;
  using ConstSharedPtr =
    std::shared_ptr<interfaces::action::MoveTime_Goal_<ContainerAllocator> const>;

  template<typename Deleter = std::default_delete<
      interfaces::action::MoveTime_Goal_<ContainerAllocator>>>
  using UniquePtrWithDeleter =
    std::unique_ptr<interfaces::action::MoveTime_Goal_<ContainerAllocator>, Deleter>;

  using UniquePtr = UniquePtrWithDeleter<>;

  template<typename Deleter = std::default_delete<
      interfaces::action::MoveTime_Goal_<ContainerAllocator>>>
  using ConstUniquePtrWithDeleter =
    std::unique_ptr<interfaces::action::MoveTime_Goal_<ContainerAllocator> const, Deleter>;
  using ConstUniquePtr = ConstUniquePtrWithDeleter<>;

  using WeakPtr =
    std::weak_ptr<interfaces::action::MoveTime_Goal_<ContainerAllocator>>;
  using ConstWeakPtr =
    std::weak_ptr<interfaces::action::MoveTime_Goal_<ContainerAllocator> const>;

  // pointer types similar to ROS 1, use SharedPtr / ConstSharedPtr instead
  // NOTE: Can't use 'using' here because GNU C++ can't parse attributes properly
  typedef DEPRECATED__interfaces__action__MoveTime_Goal
    std::shared_ptr<interfaces::action::MoveTime_Goal_<ContainerAllocator>>
    Ptr;
  typedef DEPRECATED__interfaces__action__MoveTime_Goal
    std::shared_ptr<interfaces::action::MoveTime_Goal_<ContainerAllocator> const>
    ConstPtr;

  // comparison operators
  bool operator==(const MoveTime_Goal_ & other) const
  {
    if (this->runtime != other.runtime) {
      return false;
    }
    return true;
  }
  bool operator!=(const MoveTime_Goal_ & other) const
  {
    return !this->operator==(other);
  }
};  // struct MoveTime_Goal_

// alias to use template instance with default allocator
using MoveTime_Goal =
  interfaces::action::MoveTime_Goal_<std::allocator<void>>;

// constant definitions

}  // namespace action

}  // namespace interfaces


#ifndef _WIN32
# define DEPRECATED__interfaces__action__MoveTime_Result __attribute__((deprecated))
#else
# define DEPRECATED__interfaces__action__MoveTime_Result __declspec(deprecated)
#endif

namespace interfaces
{

namespace action
{

// message struct
template<class ContainerAllocator>
struct MoveTime_Result_
{
  using Type = MoveTime_Result_<ContainerAllocator>;

  explicit MoveTime_Result_(rosidl_runtime_cpp::MessageInitialization _init = rosidl_runtime_cpp::MessageInitialization::ALL)
  {
    if (rosidl_runtime_cpp::MessageInitialization::ALL == _init ||
      rosidl_runtime_cpp::MessageInitialization::ZERO == _init)
    {
      this->finish = "";
    }
  }

  explicit MoveTime_Result_(const ContainerAllocator & _alloc, rosidl_runtime_cpp::MessageInitialization _init = rosidl_runtime_cpp::MessageInitialization::ALL)
  : finish(_alloc)
  {
    if (rosidl_runtime_cpp::MessageInitialization::ALL == _init ||
      rosidl_runtime_cpp::MessageInitialization::ZERO == _init)
    {
      this->finish = "";
    }
  }

  // field types and members
  using _finish_type =
    std::basic_string<char, std::char_traits<char>, typename std::allocator_traits<ContainerAllocator>::template rebind_alloc<char>>;
  _finish_type finish;

  // setters for named parameter idiom
  Type & set__finish(
    const std::basic_string<char, std::char_traits<char>, typename std::allocator_traits<ContainerAllocator>::template rebind_alloc<char>> & _arg)
  {
    this->finish = _arg;
    return *this;
  }

  // constant declarations

  // pointer types
  using RawPtr =
    interfaces::action::MoveTime_Result_<ContainerAllocator> *;
  using ConstRawPtr =
    const interfaces::action::MoveTime_Result_<ContainerAllocator> *;
  using SharedPtr =
    std::shared_ptr<interfaces::action::MoveTime_Result_<ContainerAllocator>>;
  using ConstSharedPtr =
    std::shared_ptr<interfaces::action::MoveTime_Result_<ContainerAllocator> const>;

  template<typename Deleter = std::default_delete<
      interfaces::action::MoveTime_Result_<ContainerAllocator>>>
  using UniquePtrWithDeleter =
    std::unique_ptr<interfaces::action::MoveTime_Result_<ContainerAllocator>, Deleter>;

  using UniquePtr = UniquePtrWithDeleter<>;

  template<typename Deleter = std::default_delete<
      interfaces::action::MoveTime_Result_<ContainerAllocator>>>
  using ConstUniquePtrWithDeleter =
    std::unique_ptr<interfaces::action::MoveTime_Result_<ContainerAllocator> const, Deleter>;
  using ConstUniquePtr = ConstUniquePtrWithDeleter<>;

  using WeakPtr =
    std::weak_ptr<interfaces::action::MoveTime_Result_<ContainerAllocator>>;
  using ConstWeakPtr =
    std::weak_ptr<interfaces::action::MoveTime_Result_<ContainerAllocator> const>;

  // pointer types similar to ROS 1, use SharedPtr / ConstSharedPtr instead
  // NOTE: Can't use 'using' here because GNU C++ can't parse attributes properly
  typedef DEPRECATED__interfaces__action__MoveTime_Result
    std::shared_ptr<interfaces::action::MoveTime_Result_<ContainerAllocator>>
    Ptr;
  typedef DEPRECATED__interfaces__action__MoveTime_Result
    std::shared_ptr<interfaces::action::MoveTime_Result_<ContainerAllocator> const>
    ConstPtr;

  // comparison operators
  bool operator==(const MoveTime_Result_ & other) const
  {
    if (this->finish != other.finish) {
      return false;
    }
    return true;
  }
  bool operator!=(const MoveTime_Result_ & other) const
  {
    return !this->operator==(other);
  }
};  // struct MoveTime_Result_

// alias to use template instance with default allocator
using MoveTime_Result =
  interfaces::action::MoveTime_Result_<std::allocator<void>>;

// constant definitions

}  // namespace action

}  // namespace interfaces


#ifndef _WIN32
# define DEPRECATED__interfaces__action__MoveTime_Feedback __attribute__((deprecated))
#else
# define DEPRECATED__interfaces__action__MoveTime_Feedback __declspec(deprecated)
#endif

namespace interfaces
{

namespace action
{

// message struct
template<class ContainerAllocator>
struct MoveTime_Feedback_
{
  using Type = MoveTime_Feedback_<ContainerAllocator>;

  explicit MoveTime_Feedback_(rosidl_runtime_cpp::MessageInitialization _init = rosidl_runtime_cpp::MessageInitialization::ALL)
  {
    if (rosidl_runtime_cpp::MessageInitialization::ALL == _init ||
      rosidl_runtime_cpp::MessageInitialization::ZERO == _init)
    {
      this->feedback = 0l;
    }
  }

  explicit MoveTime_Feedback_(const ContainerAllocator & _alloc, rosidl_runtime_cpp::MessageInitialization _init = rosidl_runtime_cpp::MessageInitialization::ALL)
  {
    (void)_alloc;
    if (rosidl_runtime_cpp::MessageInitialization::ALL == _init ||
      rosidl_runtime_cpp::MessageInitialization::ZERO == _init)
    {
      this->feedback = 0l;
    }
  }

  // field types and members
  using _feedback_type =
    int32_t;
  _feedback_type feedback;

  // setters for named parameter idiom
  Type & set__feedback(
    const int32_t & _arg)
  {
    this->feedback = _arg;
    return *this;
  }

  // constant declarations

  // pointer types
  using RawPtr =
    interfaces::action::MoveTime_Feedback_<ContainerAllocator> *;
  using ConstRawPtr =
    const interfaces::action::MoveTime_Feedback_<ContainerAllocator> *;
  using SharedPtr =
    std::shared_ptr<interfaces::action::MoveTime_Feedback_<ContainerAllocator>>;
  using ConstSharedPtr =
    std::shared_ptr<interfaces::action::MoveTime_Feedback_<ContainerAllocator> const>;

  template<typename Deleter = std::default_delete<
      interfaces::action::MoveTime_Feedback_<ContainerAllocator>>>
  using UniquePtrWithDeleter =
    std::unique_ptr<interfaces::action::MoveTime_Feedback_<ContainerAllocator>, Deleter>;

  using UniquePtr = UniquePtrWithDeleter<>;

  template<typename Deleter = std::default_delete<
      interfaces::action::MoveTime_Feedback_<ContainerAllocator>>>
  using ConstUniquePtrWithDeleter =
    std::unique_ptr<interfaces::action::MoveTime_Feedback_<ContainerAllocator> const, Deleter>;
  using ConstUniquePtr = ConstUniquePtrWithDeleter<>;

  using WeakPtr =
    std::weak_ptr<interfaces::action::MoveTime_Feedback_<ContainerAllocator>>;
  using ConstWeakPtr =
    std::weak_ptr<interfaces::action::MoveTime_Feedback_<ContainerAllocator> const>;

  // pointer types similar to ROS 1, use SharedPtr / ConstSharedPtr instead
  // NOTE: Can't use 'using' here because GNU C++ can't parse attributes properly
  typedef DEPRECATED__interfaces__action__MoveTime_Feedback
    std::shared_ptr<interfaces::action::MoveTime_Feedback_<ContainerAllocator>>
    Ptr;
  typedef DEPRECATED__interfaces__action__MoveTime_Feedback
    std::shared_ptr<interfaces::action::MoveTime_Feedback_<ContainerAllocator> const>
    ConstPtr;

  // comparison operators
  bool operator==(const MoveTime_Feedback_ & other) const
  {
    if (this->feedback != other.feedback) {
      return false;
    }
    return true;
  }
  bool operator!=(const MoveTime_Feedback_ & other) const
  {
    return !this->operator==(other);
  }
};  // struct MoveTime_Feedback_

// alias to use template instance with default allocator
using MoveTime_Feedback =
  interfaces::action::MoveTime_Feedback_<std::allocator<void>>;

// constant definitions

}  // namespace action

}  // namespace interfaces


// Include directives for member types
// Member 'goal_id'
#include "unique_identifier_msgs/msg/detail/uuid__struct.hpp"
// Member 'goal'
#include "interfaces/action/detail/move_time__struct.hpp"

#ifndef _WIN32
# define DEPRECATED__interfaces__action__MoveTime_SendGoal_Request __attribute__((deprecated))
#else
# define DEPRECATED__interfaces__action__MoveTime_SendGoal_Request __declspec(deprecated)
#endif

namespace interfaces
{

namespace action
{

// message struct
template<class ContainerAllocator>
struct MoveTime_SendGoal_Request_
{
  using Type = MoveTime_SendGoal_Request_<ContainerAllocator>;

  explicit MoveTime_SendGoal_Request_(rosidl_runtime_cpp::MessageInitialization _init = rosidl_runtime_cpp::MessageInitialization::ALL)
  : goal_id(_init),
    goal(_init)
  {
    (void)_init;
  }

  explicit MoveTime_SendGoal_Request_(const ContainerAllocator & _alloc, rosidl_runtime_cpp::MessageInitialization _init = rosidl_runtime_cpp::MessageInitialization::ALL)
  : goal_id(_alloc, _init),
    goal(_alloc, _init)
  {
    (void)_init;
  }

  // field types and members
  using _goal_id_type =
    unique_identifier_msgs::msg::UUID_<ContainerAllocator>;
  _goal_id_type goal_id;
  using _goal_type =
    interfaces::action::MoveTime_Goal_<ContainerAllocator>;
  _goal_type goal;

  // setters for named parameter idiom
  Type & set__goal_id(
    const unique_identifier_msgs::msg::UUID_<ContainerAllocator> & _arg)
  {
    this->goal_id = _arg;
    return *this;
  }
  Type & set__goal(
    const interfaces::action::MoveTime_Goal_<ContainerAllocator> & _arg)
  {
    this->goal = _arg;
    return *this;
  }

  // constant declarations

  // pointer types
  using RawPtr =
    interfaces::action::MoveTime_SendGoal_Request_<ContainerAllocator> *;
  using ConstRawPtr =
    const interfaces::action::MoveTime_SendGoal_Request_<ContainerAllocator> *;
  using SharedPtr =
    std::shared_ptr<interfaces::action::MoveTime_SendGoal_Request_<ContainerAllocator>>;
  using ConstSharedPtr =
    std::shared_ptr<interfaces::action::MoveTime_SendGoal_Request_<ContainerAllocator> const>;

  template<typename Deleter = std::default_delete<
      interfaces::action::MoveTime_SendGoal_Request_<ContainerAllocator>>>
  using UniquePtrWithDeleter =
    std::unique_ptr<interfaces::action::MoveTime_SendGoal_Request_<ContainerAllocator>, Deleter>;

  using UniquePtr = UniquePtrWithDeleter<>;

  template<typename Deleter = std::default_delete<
      interfaces::action::MoveTime_SendGoal_Request_<ContainerAllocator>>>
  using ConstUniquePtrWithDeleter =
    std::unique_ptr<interfaces::action::MoveTime_SendGoal_Request_<ContainerAllocator> const, Deleter>;
  using ConstUniquePtr = ConstUniquePtrWithDeleter<>;

  using WeakPtr =
    std::weak_ptr<interfaces::action::MoveTime_SendGoal_Request_<ContainerAllocator>>;
  using ConstWeakPtr =
    std::weak_ptr<interfaces::action::MoveTime_SendGoal_Request_<ContainerAllocator> const>;

  // pointer types similar to ROS 1, use SharedPtr / ConstSharedPtr instead
  // NOTE: Can't use 'using' here because GNU C++ can't parse attributes properly
  typedef DEPRECATED__interfaces__action__MoveTime_SendGoal_Request
    std::shared_ptr<interfaces::action::MoveTime_SendGoal_Request_<ContainerAllocator>>
    Ptr;
  typedef DEPRECATED__interfaces__action__MoveTime_SendGoal_Request
    std::shared_ptr<interfaces::action::MoveTime_SendGoal_Request_<ContainerAllocator> const>
    ConstPtr;

  // comparison operators
  bool operator==(const MoveTime_SendGoal_Request_ & other) const
  {
    if (this->goal_id != other.goal_id) {
      return false;
    }
    if (this->goal != other.goal) {
      return false;
    }
    return true;
  }
  bool operator!=(const MoveTime_SendGoal_Request_ & other) const
  {
    return !this->operator==(other);
  }
};  // struct MoveTime_SendGoal_Request_

// alias to use template instance with default allocator
using MoveTime_SendGoal_Request =
  interfaces::action::MoveTime_SendGoal_Request_<std::allocator<void>>;

// constant definitions

}  // namespace action

}  // namespace interfaces


// Include directives for member types
// Member 'stamp'
#include "builtin_interfaces/msg/detail/time__struct.hpp"

#ifndef _WIN32
# define DEPRECATED__interfaces__action__MoveTime_SendGoal_Response __attribute__((deprecated))
#else
# define DEPRECATED__interfaces__action__MoveTime_SendGoal_Response __declspec(deprecated)
#endif

namespace interfaces
{

namespace action
{

// message struct
template<class ContainerAllocator>
struct MoveTime_SendGoal_Response_
{
  using Type = MoveTime_SendGoal_Response_<ContainerAllocator>;

  explicit MoveTime_SendGoal_Response_(rosidl_runtime_cpp::MessageInitialization _init = rosidl_runtime_cpp::MessageInitialization::ALL)
  : stamp(_init)
  {
    if (rosidl_runtime_cpp::MessageInitialization::ALL == _init ||
      rosidl_runtime_cpp::MessageInitialization::ZERO == _init)
    {
      this->accepted = false;
    }
  }

  explicit MoveTime_SendGoal_Response_(const ContainerAllocator & _alloc, rosidl_runtime_cpp::MessageInitialization _init = rosidl_runtime_cpp::MessageInitialization::ALL)
  : stamp(_alloc, _init)
  {
    if (rosidl_runtime_cpp::MessageInitialization::ALL == _init ||
      rosidl_runtime_cpp::MessageInitialization::ZERO == _init)
    {
      this->accepted = false;
    }
  }

  // field types and members
  using _accepted_type =
    bool;
  _accepted_type accepted;
  using _stamp_type =
    builtin_interfaces::msg::Time_<ContainerAllocator>;
  _stamp_type stamp;

  // setters for named parameter idiom
  Type & set__accepted(
    const bool & _arg)
  {
    this->accepted = _arg;
    return *this;
  }
  Type & set__stamp(
    const builtin_interfaces::msg::Time_<ContainerAllocator> & _arg)
  {
    this->stamp = _arg;
    return *this;
  }

  // constant declarations

  // pointer types
  using RawPtr =
    interfaces::action::MoveTime_SendGoal_Response_<ContainerAllocator> *;
  using ConstRawPtr =
    const interfaces::action::MoveTime_SendGoal_Response_<ContainerAllocator> *;
  using SharedPtr =
    std::shared_ptr<interfaces::action::MoveTime_SendGoal_Response_<ContainerAllocator>>;
  using ConstSharedPtr =
    std::shared_ptr<interfaces::action::MoveTime_SendGoal_Response_<ContainerAllocator> const>;

  template<typename Deleter = std::default_delete<
      interfaces::action::MoveTime_SendGoal_Response_<ContainerAllocator>>>
  using UniquePtrWithDeleter =
    std::unique_ptr<interfaces::action::MoveTime_SendGoal_Response_<ContainerAllocator>, Deleter>;

  using UniquePtr = UniquePtrWithDeleter<>;

  template<typename Deleter = std::default_delete<
      interfaces::action::MoveTime_SendGoal_Response_<ContainerAllocator>>>
  using ConstUniquePtrWithDeleter =
    std::unique_ptr<interfaces::action::MoveTime_SendGoal_Response_<ContainerAllocator> const, Deleter>;
  using ConstUniquePtr = ConstUniquePtrWithDeleter<>;

  using WeakPtr =
    std::weak_ptr<interfaces::action::MoveTime_SendGoal_Response_<ContainerAllocator>>;
  using ConstWeakPtr =
    std::weak_ptr<interfaces::action::MoveTime_SendGoal_Response_<ContainerAllocator> const>;

  // pointer types similar to ROS 1, use SharedPtr / ConstSharedPtr instead
  // NOTE: Can't use 'using' here because GNU C++ can't parse attributes properly
  typedef DEPRECATED__interfaces__action__MoveTime_SendGoal_Response
    std::shared_ptr<interfaces::action::MoveTime_SendGoal_Response_<ContainerAllocator>>
    Ptr;
  typedef DEPRECATED__interfaces__action__MoveTime_SendGoal_Response
    std::shared_ptr<interfaces::action::MoveTime_SendGoal_Response_<ContainerAllocator> const>
    ConstPtr;

  // comparison operators
  bool operator==(const MoveTime_SendGoal_Response_ & other) const
  {
    if (this->accepted != other.accepted) {
      return false;
    }
    if (this->stamp != other.stamp) {
      return false;
    }
    return true;
  }
  bool operator!=(const MoveTime_SendGoal_Response_ & other) const
  {
    return !this->operator==(other);
  }
};  // struct MoveTime_SendGoal_Response_

// alias to use template instance with default allocator
using MoveTime_SendGoal_Response =
  interfaces::action::MoveTime_SendGoal_Response_<std::allocator<void>>;

// constant definitions

}  // namespace action

}  // namespace interfaces

namespace interfaces
{

namespace action
{

struct MoveTime_SendGoal
{
  using Request = interfaces::action::MoveTime_SendGoal_Request;
  using Response = interfaces::action::MoveTime_SendGoal_Response;
};

}  // namespace action

}  // namespace interfaces


// Include directives for member types
// Member 'goal_id'
// already included above
// #include "unique_identifier_msgs/msg/detail/uuid__struct.hpp"

#ifndef _WIN32
# define DEPRECATED__interfaces__action__MoveTime_GetResult_Request __attribute__((deprecated))
#else
# define DEPRECATED__interfaces__action__MoveTime_GetResult_Request __declspec(deprecated)
#endif

namespace interfaces
{

namespace action
{

// message struct
template<class ContainerAllocator>
struct MoveTime_GetResult_Request_
{
  using Type = MoveTime_GetResult_Request_<ContainerAllocator>;

  explicit MoveTime_GetResult_Request_(rosidl_runtime_cpp::MessageInitialization _init = rosidl_runtime_cpp::MessageInitialization::ALL)
  : goal_id(_init)
  {
    (void)_init;
  }

  explicit MoveTime_GetResult_Request_(const ContainerAllocator & _alloc, rosidl_runtime_cpp::MessageInitialization _init = rosidl_runtime_cpp::MessageInitialization::ALL)
  : goal_id(_alloc, _init)
  {
    (void)_init;
  }

  // field types and members
  using _goal_id_type =
    unique_identifier_msgs::msg::UUID_<ContainerAllocator>;
  _goal_id_type goal_id;

  // setters for named parameter idiom
  Type & set__goal_id(
    const unique_identifier_msgs::msg::UUID_<ContainerAllocator> & _arg)
  {
    this->goal_id = _arg;
    return *this;
  }

  // constant declarations

  // pointer types
  using RawPtr =
    interfaces::action::MoveTime_GetResult_Request_<ContainerAllocator> *;
  using ConstRawPtr =
    const interfaces::action::MoveTime_GetResult_Request_<ContainerAllocator> *;
  using SharedPtr =
    std::shared_ptr<interfaces::action::MoveTime_GetResult_Request_<ContainerAllocator>>;
  using ConstSharedPtr =
    std::shared_ptr<interfaces::action::MoveTime_GetResult_Request_<ContainerAllocator> const>;

  template<typename Deleter = std::default_delete<
      interfaces::action::MoveTime_GetResult_Request_<ContainerAllocator>>>
  using UniquePtrWithDeleter =
    std::unique_ptr<interfaces::action::MoveTime_GetResult_Request_<ContainerAllocator>, Deleter>;

  using UniquePtr = UniquePtrWithDeleter<>;

  template<typename Deleter = std::default_delete<
      interfaces::action::MoveTime_GetResult_Request_<ContainerAllocator>>>
  using ConstUniquePtrWithDeleter =
    std::unique_ptr<interfaces::action::MoveTime_GetResult_Request_<ContainerAllocator> const, Deleter>;
  using ConstUniquePtr = ConstUniquePtrWithDeleter<>;

  using WeakPtr =
    std::weak_ptr<interfaces::action::MoveTime_GetResult_Request_<ContainerAllocator>>;
  using ConstWeakPtr =
    std::weak_ptr<interfaces::action::MoveTime_GetResult_Request_<ContainerAllocator> const>;

  // pointer types similar to ROS 1, use SharedPtr / ConstSharedPtr instead
  // NOTE: Can't use 'using' here because GNU C++ can't parse attributes properly
  typedef DEPRECATED__interfaces__action__MoveTime_GetResult_Request
    std::shared_ptr<interfaces::action::MoveTime_GetResult_Request_<ContainerAllocator>>
    Ptr;
  typedef DEPRECATED__interfaces__action__MoveTime_GetResult_Request
    std::shared_ptr<interfaces::action::MoveTime_GetResult_Request_<ContainerAllocator> const>
    ConstPtr;

  // comparison operators
  bool operator==(const MoveTime_GetResult_Request_ & other) const
  {
    if (this->goal_id != other.goal_id) {
      return false;
    }
    return true;
  }
  bool operator!=(const MoveTime_GetResult_Request_ & other) const
  {
    return !this->operator==(other);
  }
};  // struct MoveTime_GetResult_Request_

// alias to use template instance with default allocator
using MoveTime_GetResult_Request =
  interfaces::action::MoveTime_GetResult_Request_<std::allocator<void>>;

// constant definitions

}  // namespace action

}  // namespace interfaces


// Include directives for member types
// Member 'result'
// already included above
// #include "interfaces/action/detail/move_time__struct.hpp"

#ifndef _WIN32
# define DEPRECATED__interfaces__action__MoveTime_GetResult_Response __attribute__((deprecated))
#else
# define DEPRECATED__interfaces__action__MoveTime_GetResult_Response __declspec(deprecated)
#endif

namespace interfaces
{

namespace action
{

// message struct
template<class ContainerAllocator>
struct MoveTime_GetResult_Response_
{
  using Type = MoveTime_GetResult_Response_<ContainerAllocator>;

  explicit MoveTime_GetResult_Response_(rosidl_runtime_cpp::MessageInitialization _init = rosidl_runtime_cpp::MessageInitialization::ALL)
  : result(_init)
  {
    if (rosidl_runtime_cpp::MessageInitialization::ALL == _init ||
      rosidl_runtime_cpp::MessageInitialization::ZERO == _init)
    {
      this->status = 0;
    }
  }

  explicit MoveTime_GetResult_Response_(const ContainerAllocator & _alloc, rosidl_runtime_cpp::MessageInitialization _init = rosidl_runtime_cpp::MessageInitialization::ALL)
  : result(_alloc, _init)
  {
    if (rosidl_runtime_cpp::MessageInitialization::ALL == _init ||
      rosidl_runtime_cpp::MessageInitialization::ZERO == _init)
    {
      this->status = 0;
    }
  }

  // field types and members
  using _status_type =
    int8_t;
  _status_type status;
  using _result_type =
    interfaces::action::MoveTime_Result_<ContainerAllocator>;
  _result_type result;

  // setters for named parameter idiom
  Type & set__status(
    const int8_t & _arg)
  {
    this->status = _arg;
    return *this;
  }
  Type & set__result(
    const interfaces::action::MoveTime_Result_<ContainerAllocator> & _arg)
  {
    this->result = _arg;
    return *this;
  }

  // constant declarations

  // pointer types
  using RawPtr =
    interfaces::action::MoveTime_GetResult_Response_<ContainerAllocator> *;
  using ConstRawPtr =
    const interfaces::action::MoveTime_GetResult_Response_<ContainerAllocator> *;
  using SharedPtr =
    std::shared_ptr<interfaces::action::MoveTime_GetResult_Response_<ContainerAllocator>>;
  using ConstSharedPtr =
    std::shared_ptr<interfaces::action::MoveTime_GetResult_Response_<ContainerAllocator> const>;

  template<typename Deleter = std::default_delete<
      interfaces::action::MoveTime_GetResult_Response_<ContainerAllocator>>>
  using UniquePtrWithDeleter =
    std::unique_ptr<interfaces::action::MoveTime_GetResult_Response_<ContainerAllocator>, Deleter>;

  using UniquePtr = UniquePtrWithDeleter<>;

  template<typename Deleter = std::default_delete<
      interfaces::action::MoveTime_GetResult_Response_<ContainerAllocator>>>
  using ConstUniquePtrWithDeleter =
    std::unique_ptr<interfaces::action::MoveTime_GetResult_Response_<ContainerAllocator> const, Deleter>;
  using ConstUniquePtr = ConstUniquePtrWithDeleter<>;

  using WeakPtr =
    std::weak_ptr<interfaces::action::MoveTime_GetResult_Response_<ContainerAllocator>>;
  using ConstWeakPtr =
    std::weak_ptr<interfaces::action::MoveTime_GetResult_Response_<ContainerAllocator> const>;

  // pointer types similar to ROS 1, use SharedPtr / ConstSharedPtr instead
  // NOTE: Can't use 'using' here because GNU C++ can't parse attributes properly
  typedef DEPRECATED__interfaces__action__MoveTime_GetResult_Response
    std::shared_ptr<interfaces::action::MoveTime_GetResult_Response_<ContainerAllocator>>
    Ptr;
  typedef DEPRECATED__interfaces__action__MoveTime_GetResult_Response
    std::shared_ptr<interfaces::action::MoveTime_GetResult_Response_<ContainerAllocator> const>
    ConstPtr;

  // comparison operators
  bool operator==(const MoveTime_GetResult_Response_ & other) const
  {
    if (this->status != other.status) {
      return false;
    }
    if (this->result != other.result) {
      return false;
    }
    return true;
  }
  bool operator!=(const MoveTime_GetResult_Response_ & other) const
  {
    return !this->operator==(other);
  }
};  // struct MoveTime_GetResult_Response_

// alias to use template instance with default allocator
using MoveTime_GetResult_Response =
  interfaces::action::MoveTime_GetResult_Response_<std::allocator<void>>;

// constant definitions

}  // namespace action

}  // namespace interfaces

namespace interfaces
{

namespace action
{

struct MoveTime_GetResult
{
  using Request = interfaces::action::MoveTime_GetResult_Request;
  using Response = interfaces::action::MoveTime_GetResult_Response;
};

}  // namespace action

}  // namespace interfaces


// Include directives for member types
// Member 'goal_id'
// already included above
// #include "unique_identifier_msgs/msg/detail/uuid__struct.hpp"
// Member 'feedback'
// already included above
// #include "interfaces/action/detail/move_time__struct.hpp"

#ifndef _WIN32
# define DEPRECATED__interfaces__action__MoveTime_FeedbackMessage __attribute__((deprecated))
#else
# define DEPRECATED__interfaces__action__MoveTime_FeedbackMessage __declspec(deprecated)
#endif

namespace interfaces
{

namespace action
{

// message struct
template<class ContainerAllocator>
struct MoveTime_FeedbackMessage_
{
  using Type = MoveTime_FeedbackMessage_<ContainerAllocator>;

  explicit MoveTime_FeedbackMessage_(rosidl_runtime_cpp::MessageInitialization _init = rosidl_runtime_cpp::MessageInitialization::ALL)
  : goal_id(_init),
    feedback(_init)
  {
    (void)_init;
  }

  explicit MoveTime_FeedbackMessage_(const ContainerAllocator & _alloc, rosidl_runtime_cpp::MessageInitialization _init = rosidl_runtime_cpp::MessageInitialization::ALL)
  : goal_id(_alloc, _init),
    feedback(_alloc, _init)
  {
    (void)_init;
  }

  // field types and members
  using _goal_id_type =
    unique_identifier_msgs::msg::UUID_<ContainerAllocator>;
  _goal_id_type goal_id;
  using _feedback_type =
    interfaces::action::MoveTime_Feedback_<ContainerAllocator>;
  _feedback_type feedback;

  // setters for named parameter idiom
  Type & set__goal_id(
    const unique_identifier_msgs::msg::UUID_<ContainerAllocator> & _arg)
  {
    this->goal_id = _arg;
    return *this;
  }
  Type & set__feedback(
    const interfaces::action::MoveTime_Feedback_<ContainerAllocator> & _arg)
  {
    this->feedback = _arg;
    return *this;
  }

  // constant declarations

  // pointer types
  using RawPtr =
    interfaces::action::MoveTime_FeedbackMessage_<ContainerAllocator> *;
  using ConstRawPtr =
    const interfaces::action::MoveTime_FeedbackMessage_<ContainerAllocator> *;
  using SharedPtr =
    std::shared_ptr<interfaces::action::MoveTime_FeedbackMessage_<ContainerAllocator>>;
  using ConstSharedPtr =
    std::shared_ptr<interfaces::action::MoveTime_FeedbackMessage_<ContainerAllocator> const>;

  template<typename Deleter = std::default_delete<
      interfaces::action::MoveTime_FeedbackMessage_<ContainerAllocator>>>
  using UniquePtrWithDeleter =
    std::unique_ptr<interfaces::action::MoveTime_FeedbackMessage_<ContainerAllocator>, Deleter>;

  using UniquePtr = UniquePtrWithDeleter<>;

  template<typename Deleter = std::default_delete<
      interfaces::action::MoveTime_FeedbackMessage_<ContainerAllocator>>>
  using ConstUniquePtrWithDeleter =
    std::unique_ptr<interfaces::action::MoveTime_FeedbackMessage_<ContainerAllocator> const, Deleter>;
  using ConstUniquePtr = ConstUniquePtrWithDeleter<>;

  using WeakPtr =
    std::weak_ptr<interfaces::action::MoveTime_FeedbackMessage_<ContainerAllocator>>;
  using ConstWeakPtr =
    std::weak_ptr<interfaces::action::MoveTime_FeedbackMessage_<ContainerAllocator> const>;

  // pointer types similar to ROS 1, use SharedPtr / ConstSharedPtr instead
  // NOTE: Can't use 'using' here because GNU C++ can't parse attributes properly
  typedef DEPRECATED__interfaces__action__MoveTime_FeedbackMessage
    std::shared_ptr<interfaces::action::MoveTime_FeedbackMessage_<ContainerAllocator>>
    Ptr;
  typedef DEPRECATED__interfaces__action__MoveTime_FeedbackMessage
    std::shared_ptr<interfaces::action::MoveTime_FeedbackMessage_<ContainerAllocator> const>
    ConstPtr;

  // comparison operators
  bool operator==(const MoveTime_FeedbackMessage_ & other) const
  {
    if (this->goal_id != other.goal_id) {
      return false;
    }
    if (this->feedback != other.feedback) {
      return false;
    }
    return true;
  }
  bool operator!=(const MoveTime_FeedbackMessage_ & other) const
  {
    return !this->operator==(other);
  }
};  // struct MoveTime_FeedbackMessage_

// alias to use template instance with default allocator
using MoveTime_FeedbackMessage =
  interfaces::action::MoveTime_FeedbackMessage_<std::allocator<void>>;

// constant definitions

}  // namespace action

}  // namespace interfaces

#include "action_msgs/srv/cancel_goal.hpp"
#include "action_msgs/msg/goal_info.hpp"
#include "action_msgs/msg/goal_status_array.hpp"

namespace interfaces
{

namespace action
{

struct MoveTime
{
  /// The goal message defined in the action definition.
  using Goal = interfaces::action::MoveTime_Goal;
  /// The result message defined in the action definition.
  using Result = interfaces::action::MoveTime_Result;
  /// The feedback message defined in the action definition.
  using Feedback = interfaces::action::MoveTime_Feedback;

  struct Impl
  {
    /// The send_goal service using a wrapped version of the goal message as a request.
    using SendGoalService = interfaces::action::MoveTime_SendGoal;
    /// The get_result service using a wrapped version of the result message as a response.
    using GetResultService = interfaces::action::MoveTime_GetResult;
    /// The feedback message with generic fields which wraps the feedback message.
    using FeedbackMessage = interfaces::action::MoveTime_FeedbackMessage;

    /// The generic service to cancel a goal.
    using CancelGoalService = action_msgs::srv::CancelGoal;
    /// The generic message for the status of a goal.
    using GoalStatusMessage = action_msgs::msg::GoalStatusArray;
  };
};

typedef struct MoveTime MoveTime;

}  // namespace action

}  // namespace interfaces

#endif  // INTERFACES__ACTION__DETAIL__MOVE_TIME__STRUCT_HPP_
