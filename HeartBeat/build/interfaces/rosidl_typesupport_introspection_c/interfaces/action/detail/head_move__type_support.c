// generated from rosidl_typesupport_introspection_c/resource/idl__type_support.c.em
// with input from interfaces:action/HeadMove.idl
// generated code does not contain a copyright notice

#include <stddef.h>
#include "interfaces/action/detail/head_move__rosidl_typesupport_introspection_c.h"
#include "interfaces/msg/rosidl_typesupport_introspection_c__visibility_control.h"
#include "rosidl_typesupport_introspection_c/field_types.h"
#include "rosidl_typesupport_introspection_c/identifier.h"
#include "rosidl_typesupport_introspection_c/message_introspection.h"
#include "interfaces/action/detail/head_move__functions.h"
#include "interfaces/action/detail/head_move__struct.h"


// Include directives for member types
// Member `pos`
#include "rosidl_runtime_c/primitives_sequence_functions.h"
// Member `lst`
#include "rosidl_runtime_c/string_functions.h"

#ifdef __cplusplus
extern "C"
{
#endif

void interfaces__action__HeadMove_Goal__rosidl_typesupport_introspection_c__HeadMove_Goal_init_function(
  void * message_memory, enum rosidl_runtime_c__message_initialization _init)
{
  // TODO(karsten1987): initializers are not yet implemented for typesupport c
  // see https://github.com/ros2/ros2/issues/397
  (void) _init;
  interfaces__action__HeadMove_Goal__init(message_memory);
}

void interfaces__action__HeadMove_Goal__rosidl_typesupport_introspection_c__HeadMove_Goal_fini_function(void * message_memory)
{
  interfaces__action__HeadMove_Goal__fini(message_memory);
}

size_t interfaces__action__HeadMove_Goal__rosidl_typesupport_introspection_c__size_function__HeadMove_Goal__pos(
  const void * untyped_member)
{
  const rosidl_runtime_c__double__Sequence * member =
    (const rosidl_runtime_c__double__Sequence *)(untyped_member);
  return member->size;
}

const void * interfaces__action__HeadMove_Goal__rosidl_typesupport_introspection_c__get_const_function__HeadMove_Goal__pos(
  const void * untyped_member, size_t index)
{
  const rosidl_runtime_c__double__Sequence * member =
    (const rosidl_runtime_c__double__Sequence *)(untyped_member);
  return &member->data[index];
}

void * interfaces__action__HeadMove_Goal__rosidl_typesupport_introspection_c__get_function__HeadMove_Goal__pos(
  void * untyped_member, size_t index)
{
  rosidl_runtime_c__double__Sequence * member =
    (rosidl_runtime_c__double__Sequence *)(untyped_member);
  return &member->data[index];
}

void interfaces__action__HeadMove_Goal__rosidl_typesupport_introspection_c__fetch_function__HeadMove_Goal__pos(
  const void * untyped_member, size_t index, void * untyped_value)
{
  const double * item =
    ((const double *)
    interfaces__action__HeadMove_Goal__rosidl_typesupport_introspection_c__get_const_function__HeadMove_Goal__pos(untyped_member, index));
  double * value =
    (double *)(untyped_value);
  *value = *item;
}

void interfaces__action__HeadMove_Goal__rosidl_typesupport_introspection_c__assign_function__HeadMove_Goal__pos(
  void * untyped_member, size_t index, const void * untyped_value)
{
  double * item =
    ((double *)
    interfaces__action__HeadMove_Goal__rosidl_typesupport_introspection_c__get_function__HeadMove_Goal__pos(untyped_member, index));
  const double * value =
    (const double *)(untyped_value);
  *item = *value;
}

bool interfaces__action__HeadMove_Goal__rosidl_typesupport_introspection_c__resize_function__HeadMove_Goal__pos(
  void * untyped_member, size_t size)
{
  rosidl_runtime_c__double__Sequence * member =
    (rosidl_runtime_c__double__Sequence *)(untyped_member);
  rosidl_runtime_c__double__Sequence__fini(member);
  return rosidl_runtime_c__double__Sequence__init(member, size);
}

static rosidl_typesupport_introspection_c__MessageMember interfaces__action__HeadMove_Goal__rosidl_typesupport_introspection_c__HeadMove_Goal_message_member_array[2] = {
  {
    "pos",  // name
    rosidl_typesupport_introspection_c__ROS_TYPE_DOUBLE,  // type
    0,  // upper bound of string
    NULL,  // members of sub message
    true,  // is array
    0,  // array size
    false,  // is upper bound
    offsetof(interfaces__action__HeadMove_Goal, pos),  // bytes offset in struct
    NULL,  // default value
    interfaces__action__HeadMove_Goal__rosidl_typesupport_introspection_c__size_function__HeadMove_Goal__pos,  // size() function pointer
    interfaces__action__HeadMove_Goal__rosidl_typesupport_introspection_c__get_const_function__HeadMove_Goal__pos,  // get_const(index) function pointer
    interfaces__action__HeadMove_Goal__rosidl_typesupport_introspection_c__get_function__HeadMove_Goal__pos,  // get(index) function pointer
    interfaces__action__HeadMove_Goal__rosidl_typesupport_introspection_c__fetch_function__HeadMove_Goal__pos,  // fetch(index, &value) function pointer
    interfaces__action__HeadMove_Goal__rosidl_typesupport_introspection_c__assign_function__HeadMove_Goal__pos,  // assign(index, value) function pointer
    interfaces__action__HeadMove_Goal__rosidl_typesupport_introspection_c__resize_function__HeadMove_Goal__pos  // resize(index) function pointer
  },
  {
    "lst",  // name
    rosidl_typesupport_introspection_c__ROS_TYPE_STRING,  // type
    0,  // upper bound of string
    NULL,  // members of sub message
    false,  // is array
    0,  // array size
    false,  // is upper bound
    offsetof(interfaces__action__HeadMove_Goal, lst),  // bytes offset in struct
    NULL,  // default value
    NULL,  // size() function pointer
    NULL,  // get_const(index) function pointer
    NULL,  // get(index) function pointer
    NULL,  // fetch(index, &value) function pointer
    NULL,  // assign(index, value) function pointer
    NULL  // resize(index) function pointer
  }
};

static const rosidl_typesupport_introspection_c__MessageMembers interfaces__action__HeadMove_Goal__rosidl_typesupport_introspection_c__HeadMove_Goal_message_members = {
  "interfaces__action",  // message namespace
  "HeadMove_Goal",  // message name
  2,  // number of fields
  sizeof(interfaces__action__HeadMove_Goal),
  interfaces__action__HeadMove_Goal__rosidl_typesupport_introspection_c__HeadMove_Goal_message_member_array,  // message members
  interfaces__action__HeadMove_Goal__rosidl_typesupport_introspection_c__HeadMove_Goal_init_function,  // function to initialize message memory (memory has to be allocated)
  interfaces__action__HeadMove_Goal__rosidl_typesupport_introspection_c__HeadMove_Goal_fini_function  // function to terminate message instance (will not free memory)
};

// this is not const since it must be initialized on first access
// since C does not allow non-integral compile-time constants
static rosidl_message_type_support_t interfaces__action__HeadMove_Goal__rosidl_typesupport_introspection_c__HeadMove_Goal_message_type_support_handle = {
  0,
  &interfaces__action__HeadMove_Goal__rosidl_typesupport_introspection_c__HeadMove_Goal_message_members,
  get_message_typesupport_handle_function,
};

ROSIDL_TYPESUPPORT_INTROSPECTION_C_EXPORT_interfaces
const rosidl_message_type_support_t *
ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_introspection_c, interfaces, action, HeadMove_Goal)() {
  if (!interfaces__action__HeadMove_Goal__rosidl_typesupport_introspection_c__HeadMove_Goal_message_type_support_handle.typesupport_identifier) {
    interfaces__action__HeadMove_Goal__rosidl_typesupport_introspection_c__HeadMove_Goal_message_type_support_handle.typesupport_identifier =
      rosidl_typesupport_introspection_c__identifier;
  }
  return &interfaces__action__HeadMove_Goal__rosidl_typesupport_introspection_c__HeadMove_Goal_message_type_support_handle;
}
#ifdef __cplusplus
}
#endif

// already included above
// #include <stddef.h>
// already included above
// #include "interfaces/action/detail/head_move__rosidl_typesupport_introspection_c.h"
// already included above
// #include "interfaces/msg/rosidl_typesupport_introspection_c__visibility_control.h"
// already included above
// #include "rosidl_typesupport_introspection_c/field_types.h"
// already included above
// #include "rosidl_typesupport_introspection_c/identifier.h"
// already included above
// #include "rosidl_typesupport_introspection_c/message_introspection.h"
// already included above
// #include "interfaces/action/detail/head_move__functions.h"
// already included above
// #include "interfaces/action/detail/head_move__struct.h"


// Include directives for member types
// Member `finish`
// already included above
// #include "rosidl_runtime_c/string_functions.h"

#ifdef __cplusplus
extern "C"
{
#endif

void interfaces__action__HeadMove_Result__rosidl_typesupport_introspection_c__HeadMove_Result_init_function(
  void * message_memory, enum rosidl_runtime_c__message_initialization _init)
{
  // TODO(karsten1987): initializers are not yet implemented for typesupport c
  // see https://github.com/ros2/ros2/issues/397
  (void) _init;
  interfaces__action__HeadMove_Result__init(message_memory);
}

void interfaces__action__HeadMove_Result__rosidl_typesupport_introspection_c__HeadMove_Result_fini_function(void * message_memory)
{
  interfaces__action__HeadMove_Result__fini(message_memory);
}

static rosidl_typesupport_introspection_c__MessageMember interfaces__action__HeadMove_Result__rosidl_typesupport_introspection_c__HeadMove_Result_message_member_array[1] = {
  {
    "finish",  // name
    rosidl_typesupport_introspection_c__ROS_TYPE_STRING,  // type
    0,  // upper bound of string
    NULL,  // members of sub message
    false,  // is array
    0,  // array size
    false,  // is upper bound
    offsetof(interfaces__action__HeadMove_Result, finish),  // bytes offset in struct
    NULL,  // default value
    NULL,  // size() function pointer
    NULL,  // get_const(index) function pointer
    NULL,  // get(index) function pointer
    NULL,  // fetch(index, &value) function pointer
    NULL,  // assign(index, value) function pointer
    NULL  // resize(index) function pointer
  }
};

static const rosidl_typesupport_introspection_c__MessageMembers interfaces__action__HeadMove_Result__rosidl_typesupport_introspection_c__HeadMove_Result_message_members = {
  "interfaces__action",  // message namespace
  "HeadMove_Result",  // message name
  1,  // number of fields
  sizeof(interfaces__action__HeadMove_Result),
  interfaces__action__HeadMove_Result__rosidl_typesupport_introspection_c__HeadMove_Result_message_member_array,  // message members
  interfaces__action__HeadMove_Result__rosidl_typesupport_introspection_c__HeadMove_Result_init_function,  // function to initialize message memory (memory has to be allocated)
  interfaces__action__HeadMove_Result__rosidl_typesupport_introspection_c__HeadMove_Result_fini_function  // function to terminate message instance (will not free memory)
};

// this is not const since it must be initialized on first access
// since C does not allow non-integral compile-time constants
static rosidl_message_type_support_t interfaces__action__HeadMove_Result__rosidl_typesupport_introspection_c__HeadMove_Result_message_type_support_handle = {
  0,
  &interfaces__action__HeadMove_Result__rosidl_typesupport_introspection_c__HeadMove_Result_message_members,
  get_message_typesupport_handle_function,
};

ROSIDL_TYPESUPPORT_INTROSPECTION_C_EXPORT_interfaces
const rosidl_message_type_support_t *
ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_introspection_c, interfaces, action, HeadMove_Result)() {
  if (!interfaces__action__HeadMove_Result__rosidl_typesupport_introspection_c__HeadMove_Result_message_type_support_handle.typesupport_identifier) {
    interfaces__action__HeadMove_Result__rosidl_typesupport_introspection_c__HeadMove_Result_message_type_support_handle.typesupport_identifier =
      rosidl_typesupport_introspection_c__identifier;
  }
  return &interfaces__action__HeadMove_Result__rosidl_typesupport_introspection_c__HeadMove_Result_message_type_support_handle;
}
#ifdef __cplusplus
}
#endif

// already included above
// #include <stddef.h>
// already included above
// #include "interfaces/action/detail/head_move__rosidl_typesupport_introspection_c.h"
// already included above
// #include "interfaces/msg/rosidl_typesupport_introspection_c__visibility_control.h"
// already included above
// #include "rosidl_typesupport_introspection_c/field_types.h"
// already included above
// #include "rosidl_typesupport_introspection_c/identifier.h"
// already included above
// #include "rosidl_typesupport_introspection_c/message_introspection.h"
// already included above
// #include "interfaces/action/detail/head_move__functions.h"
// already included above
// #include "interfaces/action/detail/head_move__struct.h"


// Include directives for member types
// Member `ast`
// already included above
// #include "rosidl_runtime_c/string_functions.h"

#ifdef __cplusplus
extern "C"
{
#endif

void interfaces__action__HeadMove_Feedback__rosidl_typesupport_introspection_c__HeadMove_Feedback_init_function(
  void * message_memory, enum rosidl_runtime_c__message_initialization _init)
{
  // TODO(karsten1987): initializers are not yet implemented for typesupport c
  // see https://github.com/ros2/ros2/issues/397
  (void) _init;
  interfaces__action__HeadMove_Feedback__init(message_memory);
}

void interfaces__action__HeadMove_Feedback__rosidl_typesupport_introspection_c__HeadMove_Feedback_fini_function(void * message_memory)
{
  interfaces__action__HeadMove_Feedback__fini(message_memory);
}

static rosidl_typesupport_introspection_c__MessageMember interfaces__action__HeadMove_Feedback__rosidl_typesupport_introspection_c__HeadMove_Feedback_message_member_array[2] = {
  {
    "ast",  // name
    rosidl_typesupport_introspection_c__ROS_TYPE_STRING,  // type
    0,  // upper bound of string
    NULL,  // members of sub message
    false,  // is array
    0,  // array size
    false,  // is upper bound
    offsetof(interfaces__action__HeadMove_Feedback, ast),  // bytes offset in struct
    NULL,  // default value
    NULL,  // size() function pointer
    NULL,  // get_const(index) function pointer
    NULL,  // get(index) function pointer
    NULL,  // fetch(index, &value) function pointer
    NULL,  // assign(index, value) function pointer
    NULL  // resize(index) function pointer
  },
  {
    "duration",  // name
    rosidl_typesupport_introspection_c__ROS_TYPE_DOUBLE,  // type
    0,  // upper bound of string
    NULL,  // members of sub message
    false,  // is array
    0,  // array size
    false,  // is upper bound
    offsetof(interfaces__action__HeadMove_Feedback, duration),  // bytes offset in struct
    NULL,  // default value
    NULL,  // size() function pointer
    NULL,  // get_const(index) function pointer
    NULL,  // get(index) function pointer
    NULL,  // fetch(index, &value) function pointer
    NULL,  // assign(index, value) function pointer
    NULL  // resize(index) function pointer
  }
};

static const rosidl_typesupport_introspection_c__MessageMembers interfaces__action__HeadMove_Feedback__rosidl_typesupport_introspection_c__HeadMove_Feedback_message_members = {
  "interfaces__action",  // message namespace
  "HeadMove_Feedback",  // message name
  2,  // number of fields
  sizeof(interfaces__action__HeadMove_Feedback),
  interfaces__action__HeadMove_Feedback__rosidl_typesupport_introspection_c__HeadMove_Feedback_message_member_array,  // message members
  interfaces__action__HeadMove_Feedback__rosidl_typesupport_introspection_c__HeadMove_Feedback_init_function,  // function to initialize message memory (memory has to be allocated)
  interfaces__action__HeadMove_Feedback__rosidl_typesupport_introspection_c__HeadMove_Feedback_fini_function  // function to terminate message instance (will not free memory)
};

// this is not const since it must be initialized on first access
// since C does not allow non-integral compile-time constants
static rosidl_message_type_support_t interfaces__action__HeadMove_Feedback__rosidl_typesupport_introspection_c__HeadMove_Feedback_message_type_support_handle = {
  0,
  &interfaces__action__HeadMove_Feedback__rosidl_typesupport_introspection_c__HeadMove_Feedback_message_members,
  get_message_typesupport_handle_function,
};

ROSIDL_TYPESUPPORT_INTROSPECTION_C_EXPORT_interfaces
const rosidl_message_type_support_t *
ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_introspection_c, interfaces, action, HeadMove_Feedback)() {
  if (!interfaces__action__HeadMove_Feedback__rosidl_typesupport_introspection_c__HeadMove_Feedback_message_type_support_handle.typesupport_identifier) {
    interfaces__action__HeadMove_Feedback__rosidl_typesupport_introspection_c__HeadMove_Feedback_message_type_support_handle.typesupport_identifier =
      rosidl_typesupport_introspection_c__identifier;
  }
  return &interfaces__action__HeadMove_Feedback__rosidl_typesupport_introspection_c__HeadMove_Feedback_message_type_support_handle;
}
#ifdef __cplusplus
}
#endif

// already included above
// #include <stddef.h>
// already included above
// #include "interfaces/action/detail/head_move__rosidl_typesupport_introspection_c.h"
// already included above
// #include "interfaces/msg/rosidl_typesupport_introspection_c__visibility_control.h"
// already included above
// #include "rosidl_typesupport_introspection_c/field_types.h"
// already included above
// #include "rosidl_typesupport_introspection_c/identifier.h"
// already included above
// #include "rosidl_typesupport_introspection_c/message_introspection.h"
// already included above
// #include "interfaces/action/detail/head_move__functions.h"
// already included above
// #include "interfaces/action/detail/head_move__struct.h"


// Include directives for member types
// Member `goal_id`
#include "unique_identifier_msgs/msg/uuid.h"
// Member `goal_id`
#include "unique_identifier_msgs/msg/detail/uuid__rosidl_typesupport_introspection_c.h"
// Member `goal`
#include "interfaces/action/head_move.h"
// Member `goal`
// already included above
// #include "interfaces/action/detail/head_move__rosidl_typesupport_introspection_c.h"

#ifdef __cplusplus
extern "C"
{
#endif

void interfaces__action__HeadMove_SendGoal_Request__rosidl_typesupport_introspection_c__HeadMove_SendGoal_Request_init_function(
  void * message_memory, enum rosidl_runtime_c__message_initialization _init)
{
  // TODO(karsten1987): initializers are not yet implemented for typesupport c
  // see https://github.com/ros2/ros2/issues/397
  (void) _init;
  interfaces__action__HeadMove_SendGoal_Request__init(message_memory);
}

void interfaces__action__HeadMove_SendGoal_Request__rosidl_typesupport_introspection_c__HeadMove_SendGoal_Request_fini_function(void * message_memory)
{
  interfaces__action__HeadMove_SendGoal_Request__fini(message_memory);
}

static rosidl_typesupport_introspection_c__MessageMember interfaces__action__HeadMove_SendGoal_Request__rosidl_typesupport_introspection_c__HeadMove_SendGoal_Request_message_member_array[2] = {
  {
    "goal_id",  // name
    rosidl_typesupport_introspection_c__ROS_TYPE_MESSAGE,  // type
    0,  // upper bound of string
    NULL,  // members of sub message (initialized later)
    false,  // is array
    0,  // array size
    false,  // is upper bound
    offsetof(interfaces__action__HeadMove_SendGoal_Request, goal_id),  // bytes offset in struct
    NULL,  // default value
    NULL,  // size() function pointer
    NULL,  // get_const(index) function pointer
    NULL,  // get(index) function pointer
    NULL,  // fetch(index, &value) function pointer
    NULL,  // assign(index, value) function pointer
    NULL  // resize(index) function pointer
  },
  {
    "goal",  // name
    rosidl_typesupport_introspection_c__ROS_TYPE_MESSAGE,  // type
    0,  // upper bound of string
    NULL,  // members of sub message (initialized later)
    false,  // is array
    0,  // array size
    false,  // is upper bound
    offsetof(interfaces__action__HeadMove_SendGoal_Request, goal),  // bytes offset in struct
    NULL,  // default value
    NULL,  // size() function pointer
    NULL,  // get_const(index) function pointer
    NULL,  // get(index) function pointer
    NULL,  // fetch(index, &value) function pointer
    NULL,  // assign(index, value) function pointer
    NULL  // resize(index) function pointer
  }
};

static const rosidl_typesupport_introspection_c__MessageMembers interfaces__action__HeadMove_SendGoal_Request__rosidl_typesupport_introspection_c__HeadMove_SendGoal_Request_message_members = {
  "interfaces__action",  // message namespace
  "HeadMove_SendGoal_Request",  // message name
  2,  // number of fields
  sizeof(interfaces__action__HeadMove_SendGoal_Request),
  interfaces__action__HeadMove_SendGoal_Request__rosidl_typesupport_introspection_c__HeadMove_SendGoal_Request_message_member_array,  // message members
  interfaces__action__HeadMove_SendGoal_Request__rosidl_typesupport_introspection_c__HeadMove_SendGoal_Request_init_function,  // function to initialize message memory (memory has to be allocated)
  interfaces__action__HeadMove_SendGoal_Request__rosidl_typesupport_introspection_c__HeadMove_SendGoal_Request_fini_function  // function to terminate message instance (will not free memory)
};

// this is not const since it must be initialized on first access
// since C does not allow non-integral compile-time constants
static rosidl_message_type_support_t interfaces__action__HeadMove_SendGoal_Request__rosidl_typesupport_introspection_c__HeadMove_SendGoal_Request_message_type_support_handle = {
  0,
  &interfaces__action__HeadMove_SendGoal_Request__rosidl_typesupport_introspection_c__HeadMove_SendGoal_Request_message_members,
  get_message_typesupport_handle_function,
};

ROSIDL_TYPESUPPORT_INTROSPECTION_C_EXPORT_interfaces
const rosidl_message_type_support_t *
ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_introspection_c, interfaces, action, HeadMove_SendGoal_Request)() {
  interfaces__action__HeadMove_SendGoal_Request__rosidl_typesupport_introspection_c__HeadMove_SendGoal_Request_message_member_array[0].members_ =
    ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_introspection_c, unique_identifier_msgs, msg, UUID)();
  interfaces__action__HeadMove_SendGoal_Request__rosidl_typesupport_introspection_c__HeadMove_SendGoal_Request_message_member_array[1].members_ =
    ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_introspection_c, interfaces, action, HeadMove_Goal)();
  if (!interfaces__action__HeadMove_SendGoal_Request__rosidl_typesupport_introspection_c__HeadMove_SendGoal_Request_message_type_support_handle.typesupport_identifier) {
    interfaces__action__HeadMove_SendGoal_Request__rosidl_typesupport_introspection_c__HeadMove_SendGoal_Request_message_type_support_handle.typesupport_identifier =
      rosidl_typesupport_introspection_c__identifier;
  }
  return &interfaces__action__HeadMove_SendGoal_Request__rosidl_typesupport_introspection_c__HeadMove_SendGoal_Request_message_type_support_handle;
}
#ifdef __cplusplus
}
#endif

// already included above
// #include <stddef.h>
// already included above
// #include "interfaces/action/detail/head_move__rosidl_typesupport_introspection_c.h"
// already included above
// #include "interfaces/msg/rosidl_typesupport_introspection_c__visibility_control.h"
// already included above
// #include "rosidl_typesupport_introspection_c/field_types.h"
// already included above
// #include "rosidl_typesupport_introspection_c/identifier.h"
// already included above
// #include "rosidl_typesupport_introspection_c/message_introspection.h"
// already included above
// #include "interfaces/action/detail/head_move__functions.h"
// already included above
// #include "interfaces/action/detail/head_move__struct.h"


// Include directives for member types
// Member `stamp`
#include "builtin_interfaces/msg/time.h"
// Member `stamp`
#include "builtin_interfaces/msg/detail/time__rosidl_typesupport_introspection_c.h"

#ifdef __cplusplus
extern "C"
{
#endif

void interfaces__action__HeadMove_SendGoal_Response__rosidl_typesupport_introspection_c__HeadMove_SendGoal_Response_init_function(
  void * message_memory, enum rosidl_runtime_c__message_initialization _init)
{
  // TODO(karsten1987): initializers are not yet implemented for typesupport c
  // see https://github.com/ros2/ros2/issues/397
  (void) _init;
  interfaces__action__HeadMove_SendGoal_Response__init(message_memory);
}

void interfaces__action__HeadMove_SendGoal_Response__rosidl_typesupport_introspection_c__HeadMove_SendGoal_Response_fini_function(void * message_memory)
{
  interfaces__action__HeadMove_SendGoal_Response__fini(message_memory);
}

static rosidl_typesupport_introspection_c__MessageMember interfaces__action__HeadMove_SendGoal_Response__rosidl_typesupport_introspection_c__HeadMove_SendGoal_Response_message_member_array[2] = {
  {
    "accepted",  // name
    rosidl_typesupport_introspection_c__ROS_TYPE_BOOLEAN,  // type
    0,  // upper bound of string
    NULL,  // members of sub message
    false,  // is array
    0,  // array size
    false,  // is upper bound
    offsetof(interfaces__action__HeadMove_SendGoal_Response, accepted),  // bytes offset in struct
    NULL,  // default value
    NULL,  // size() function pointer
    NULL,  // get_const(index) function pointer
    NULL,  // get(index) function pointer
    NULL,  // fetch(index, &value) function pointer
    NULL,  // assign(index, value) function pointer
    NULL  // resize(index) function pointer
  },
  {
    "stamp",  // name
    rosidl_typesupport_introspection_c__ROS_TYPE_MESSAGE,  // type
    0,  // upper bound of string
    NULL,  // members of sub message (initialized later)
    false,  // is array
    0,  // array size
    false,  // is upper bound
    offsetof(interfaces__action__HeadMove_SendGoal_Response, stamp),  // bytes offset in struct
    NULL,  // default value
    NULL,  // size() function pointer
    NULL,  // get_const(index) function pointer
    NULL,  // get(index) function pointer
    NULL,  // fetch(index, &value) function pointer
    NULL,  // assign(index, value) function pointer
    NULL  // resize(index) function pointer
  }
};

static const rosidl_typesupport_introspection_c__MessageMembers interfaces__action__HeadMove_SendGoal_Response__rosidl_typesupport_introspection_c__HeadMove_SendGoal_Response_message_members = {
  "interfaces__action",  // message namespace
  "HeadMove_SendGoal_Response",  // message name
  2,  // number of fields
  sizeof(interfaces__action__HeadMove_SendGoal_Response),
  interfaces__action__HeadMove_SendGoal_Response__rosidl_typesupport_introspection_c__HeadMove_SendGoal_Response_message_member_array,  // message members
  interfaces__action__HeadMove_SendGoal_Response__rosidl_typesupport_introspection_c__HeadMove_SendGoal_Response_init_function,  // function to initialize message memory (memory has to be allocated)
  interfaces__action__HeadMove_SendGoal_Response__rosidl_typesupport_introspection_c__HeadMove_SendGoal_Response_fini_function  // function to terminate message instance (will not free memory)
};

// this is not const since it must be initialized on first access
// since C does not allow non-integral compile-time constants
static rosidl_message_type_support_t interfaces__action__HeadMove_SendGoal_Response__rosidl_typesupport_introspection_c__HeadMove_SendGoal_Response_message_type_support_handle = {
  0,
  &interfaces__action__HeadMove_SendGoal_Response__rosidl_typesupport_introspection_c__HeadMove_SendGoal_Response_message_members,
  get_message_typesupport_handle_function,
};

ROSIDL_TYPESUPPORT_INTROSPECTION_C_EXPORT_interfaces
const rosidl_message_type_support_t *
ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_introspection_c, interfaces, action, HeadMove_SendGoal_Response)() {
  interfaces__action__HeadMove_SendGoal_Response__rosidl_typesupport_introspection_c__HeadMove_SendGoal_Response_message_member_array[1].members_ =
    ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_introspection_c, builtin_interfaces, msg, Time)();
  if (!interfaces__action__HeadMove_SendGoal_Response__rosidl_typesupport_introspection_c__HeadMove_SendGoal_Response_message_type_support_handle.typesupport_identifier) {
    interfaces__action__HeadMove_SendGoal_Response__rosidl_typesupport_introspection_c__HeadMove_SendGoal_Response_message_type_support_handle.typesupport_identifier =
      rosidl_typesupport_introspection_c__identifier;
  }
  return &interfaces__action__HeadMove_SendGoal_Response__rosidl_typesupport_introspection_c__HeadMove_SendGoal_Response_message_type_support_handle;
}
#ifdef __cplusplus
}
#endif

#include "rosidl_runtime_c/service_type_support_struct.h"
// already included above
// #include "interfaces/msg/rosidl_typesupport_introspection_c__visibility_control.h"
// already included above
// #include "interfaces/action/detail/head_move__rosidl_typesupport_introspection_c.h"
// already included above
// #include "rosidl_typesupport_introspection_c/identifier.h"
#include "rosidl_typesupport_introspection_c/service_introspection.h"

// this is intentionally not const to allow initialization later to prevent an initialization race
static rosidl_typesupport_introspection_c__ServiceMembers interfaces__action__detail__head_move__rosidl_typesupport_introspection_c__HeadMove_SendGoal_service_members = {
  "interfaces__action",  // service namespace
  "HeadMove_SendGoal",  // service name
  // these two fields are initialized below on the first access
  NULL,  // request message
  // interfaces__action__detail__head_move__rosidl_typesupport_introspection_c__HeadMove_SendGoal_Request_message_type_support_handle,
  NULL  // response message
  // interfaces__action__detail__head_move__rosidl_typesupport_introspection_c__HeadMove_SendGoal_Response_message_type_support_handle
};

static rosidl_service_type_support_t interfaces__action__detail__head_move__rosidl_typesupport_introspection_c__HeadMove_SendGoal_service_type_support_handle = {
  0,
  &interfaces__action__detail__head_move__rosidl_typesupport_introspection_c__HeadMove_SendGoal_service_members,
  get_service_typesupport_handle_function,
};

// Forward declaration of request/response type support functions
const rosidl_message_type_support_t *
ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_introspection_c, interfaces, action, HeadMove_SendGoal_Request)();

const rosidl_message_type_support_t *
ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_introspection_c, interfaces, action, HeadMove_SendGoal_Response)();

ROSIDL_TYPESUPPORT_INTROSPECTION_C_EXPORT_interfaces
const rosidl_service_type_support_t *
ROSIDL_TYPESUPPORT_INTERFACE__SERVICE_SYMBOL_NAME(rosidl_typesupport_introspection_c, interfaces, action, HeadMove_SendGoal)() {
  if (!interfaces__action__detail__head_move__rosidl_typesupport_introspection_c__HeadMove_SendGoal_service_type_support_handle.typesupport_identifier) {
    interfaces__action__detail__head_move__rosidl_typesupport_introspection_c__HeadMove_SendGoal_service_type_support_handle.typesupport_identifier =
      rosidl_typesupport_introspection_c__identifier;
  }
  rosidl_typesupport_introspection_c__ServiceMembers * service_members =
    (rosidl_typesupport_introspection_c__ServiceMembers *)interfaces__action__detail__head_move__rosidl_typesupport_introspection_c__HeadMove_SendGoal_service_type_support_handle.data;

  if (!service_members->request_members_) {
    service_members->request_members_ =
      (const rosidl_typesupport_introspection_c__MessageMembers *)
      ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_introspection_c, interfaces, action, HeadMove_SendGoal_Request)()->data;
  }
  if (!service_members->response_members_) {
    service_members->response_members_ =
      (const rosidl_typesupport_introspection_c__MessageMembers *)
      ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_introspection_c, interfaces, action, HeadMove_SendGoal_Response)()->data;
  }

  return &interfaces__action__detail__head_move__rosidl_typesupport_introspection_c__HeadMove_SendGoal_service_type_support_handle;
}

// already included above
// #include <stddef.h>
// already included above
// #include "interfaces/action/detail/head_move__rosidl_typesupport_introspection_c.h"
// already included above
// #include "interfaces/msg/rosidl_typesupport_introspection_c__visibility_control.h"
// already included above
// #include "rosidl_typesupport_introspection_c/field_types.h"
// already included above
// #include "rosidl_typesupport_introspection_c/identifier.h"
// already included above
// #include "rosidl_typesupport_introspection_c/message_introspection.h"
// already included above
// #include "interfaces/action/detail/head_move__functions.h"
// already included above
// #include "interfaces/action/detail/head_move__struct.h"


// Include directives for member types
// Member `goal_id`
// already included above
// #include "unique_identifier_msgs/msg/uuid.h"
// Member `goal_id`
// already included above
// #include "unique_identifier_msgs/msg/detail/uuid__rosidl_typesupport_introspection_c.h"

#ifdef __cplusplus
extern "C"
{
#endif

void interfaces__action__HeadMove_GetResult_Request__rosidl_typesupport_introspection_c__HeadMove_GetResult_Request_init_function(
  void * message_memory, enum rosidl_runtime_c__message_initialization _init)
{
  // TODO(karsten1987): initializers are not yet implemented for typesupport c
  // see https://github.com/ros2/ros2/issues/397
  (void) _init;
  interfaces__action__HeadMove_GetResult_Request__init(message_memory);
}

void interfaces__action__HeadMove_GetResult_Request__rosidl_typesupport_introspection_c__HeadMove_GetResult_Request_fini_function(void * message_memory)
{
  interfaces__action__HeadMove_GetResult_Request__fini(message_memory);
}

static rosidl_typesupport_introspection_c__MessageMember interfaces__action__HeadMove_GetResult_Request__rosidl_typesupport_introspection_c__HeadMove_GetResult_Request_message_member_array[1] = {
  {
    "goal_id",  // name
    rosidl_typesupport_introspection_c__ROS_TYPE_MESSAGE,  // type
    0,  // upper bound of string
    NULL,  // members of sub message (initialized later)
    false,  // is array
    0,  // array size
    false,  // is upper bound
    offsetof(interfaces__action__HeadMove_GetResult_Request, goal_id),  // bytes offset in struct
    NULL,  // default value
    NULL,  // size() function pointer
    NULL,  // get_const(index) function pointer
    NULL,  // get(index) function pointer
    NULL,  // fetch(index, &value) function pointer
    NULL,  // assign(index, value) function pointer
    NULL  // resize(index) function pointer
  }
};

static const rosidl_typesupport_introspection_c__MessageMembers interfaces__action__HeadMove_GetResult_Request__rosidl_typesupport_introspection_c__HeadMove_GetResult_Request_message_members = {
  "interfaces__action",  // message namespace
  "HeadMove_GetResult_Request",  // message name
  1,  // number of fields
  sizeof(interfaces__action__HeadMove_GetResult_Request),
  interfaces__action__HeadMove_GetResult_Request__rosidl_typesupport_introspection_c__HeadMove_GetResult_Request_message_member_array,  // message members
  interfaces__action__HeadMove_GetResult_Request__rosidl_typesupport_introspection_c__HeadMove_GetResult_Request_init_function,  // function to initialize message memory (memory has to be allocated)
  interfaces__action__HeadMove_GetResult_Request__rosidl_typesupport_introspection_c__HeadMove_GetResult_Request_fini_function  // function to terminate message instance (will not free memory)
};

// this is not const since it must be initialized on first access
// since C does not allow non-integral compile-time constants
static rosidl_message_type_support_t interfaces__action__HeadMove_GetResult_Request__rosidl_typesupport_introspection_c__HeadMove_GetResult_Request_message_type_support_handle = {
  0,
  &interfaces__action__HeadMove_GetResult_Request__rosidl_typesupport_introspection_c__HeadMove_GetResult_Request_message_members,
  get_message_typesupport_handle_function,
};

ROSIDL_TYPESUPPORT_INTROSPECTION_C_EXPORT_interfaces
const rosidl_message_type_support_t *
ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_introspection_c, interfaces, action, HeadMove_GetResult_Request)() {
  interfaces__action__HeadMove_GetResult_Request__rosidl_typesupport_introspection_c__HeadMove_GetResult_Request_message_member_array[0].members_ =
    ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_introspection_c, unique_identifier_msgs, msg, UUID)();
  if (!interfaces__action__HeadMove_GetResult_Request__rosidl_typesupport_introspection_c__HeadMove_GetResult_Request_message_type_support_handle.typesupport_identifier) {
    interfaces__action__HeadMove_GetResult_Request__rosidl_typesupport_introspection_c__HeadMove_GetResult_Request_message_type_support_handle.typesupport_identifier =
      rosidl_typesupport_introspection_c__identifier;
  }
  return &interfaces__action__HeadMove_GetResult_Request__rosidl_typesupport_introspection_c__HeadMove_GetResult_Request_message_type_support_handle;
}
#ifdef __cplusplus
}
#endif

// already included above
// #include <stddef.h>
// already included above
// #include "interfaces/action/detail/head_move__rosidl_typesupport_introspection_c.h"
// already included above
// #include "interfaces/msg/rosidl_typesupport_introspection_c__visibility_control.h"
// already included above
// #include "rosidl_typesupport_introspection_c/field_types.h"
// already included above
// #include "rosidl_typesupport_introspection_c/identifier.h"
// already included above
// #include "rosidl_typesupport_introspection_c/message_introspection.h"
// already included above
// #include "interfaces/action/detail/head_move__functions.h"
// already included above
// #include "interfaces/action/detail/head_move__struct.h"


// Include directives for member types
// Member `result`
// already included above
// #include "interfaces/action/head_move.h"
// Member `result`
// already included above
// #include "interfaces/action/detail/head_move__rosidl_typesupport_introspection_c.h"

#ifdef __cplusplus
extern "C"
{
#endif

void interfaces__action__HeadMove_GetResult_Response__rosidl_typesupport_introspection_c__HeadMove_GetResult_Response_init_function(
  void * message_memory, enum rosidl_runtime_c__message_initialization _init)
{
  // TODO(karsten1987): initializers are not yet implemented for typesupport c
  // see https://github.com/ros2/ros2/issues/397
  (void) _init;
  interfaces__action__HeadMove_GetResult_Response__init(message_memory);
}

void interfaces__action__HeadMove_GetResult_Response__rosidl_typesupport_introspection_c__HeadMove_GetResult_Response_fini_function(void * message_memory)
{
  interfaces__action__HeadMove_GetResult_Response__fini(message_memory);
}

static rosidl_typesupport_introspection_c__MessageMember interfaces__action__HeadMove_GetResult_Response__rosidl_typesupport_introspection_c__HeadMove_GetResult_Response_message_member_array[2] = {
  {
    "status",  // name
    rosidl_typesupport_introspection_c__ROS_TYPE_INT8,  // type
    0,  // upper bound of string
    NULL,  // members of sub message
    false,  // is array
    0,  // array size
    false,  // is upper bound
    offsetof(interfaces__action__HeadMove_GetResult_Response, status),  // bytes offset in struct
    NULL,  // default value
    NULL,  // size() function pointer
    NULL,  // get_const(index) function pointer
    NULL,  // get(index) function pointer
    NULL,  // fetch(index, &value) function pointer
    NULL,  // assign(index, value) function pointer
    NULL  // resize(index) function pointer
  },
  {
    "result",  // name
    rosidl_typesupport_introspection_c__ROS_TYPE_MESSAGE,  // type
    0,  // upper bound of string
    NULL,  // members of sub message (initialized later)
    false,  // is array
    0,  // array size
    false,  // is upper bound
    offsetof(interfaces__action__HeadMove_GetResult_Response, result),  // bytes offset in struct
    NULL,  // default value
    NULL,  // size() function pointer
    NULL,  // get_const(index) function pointer
    NULL,  // get(index) function pointer
    NULL,  // fetch(index, &value) function pointer
    NULL,  // assign(index, value) function pointer
    NULL  // resize(index) function pointer
  }
};

static const rosidl_typesupport_introspection_c__MessageMembers interfaces__action__HeadMove_GetResult_Response__rosidl_typesupport_introspection_c__HeadMove_GetResult_Response_message_members = {
  "interfaces__action",  // message namespace
  "HeadMove_GetResult_Response",  // message name
  2,  // number of fields
  sizeof(interfaces__action__HeadMove_GetResult_Response),
  interfaces__action__HeadMove_GetResult_Response__rosidl_typesupport_introspection_c__HeadMove_GetResult_Response_message_member_array,  // message members
  interfaces__action__HeadMove_GetResult_Response__rosidl_typesupport_introspection_c__HeadMove_GetResult_Response_init_function,  // function to initialize message memory (memory has to be allocated)
  interfaces__action__HeadMove_GetResult_Response__rosidl_typesupport_introspection_c__HeadMove_GetResult_Response_fini_function  // function to terminate message instance (will not free memory)
};

// this is not const since it must be initialized on first access
// since C does not allow non-integral compile-time constants
static rosidl_message_type_support_t interfaces__action__HeadMove_GetResult_Response__rosidl_typesupport_introspection_c__HeadMove_GetResult_Response_message_type_support_handle = {
  0,
  &interfaces__action__HeadMove_GetResult_Response__rosidl_typesupport_introspection_c__HeadMove_GetResult_Response_message_members,
  get_message_typesupport_handle_function,
};

ROSIDL_TYPESUPPORT_INTROSPECTION_C_EXPORT_interfaces
const rosidl_message_type_support_t *
ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_introspection_c, interfaces, action, HeadMove_GetResult_Response)() {
  interfaces__action__HeadMove_GetResult_Response__rosidl_typesupport_introspection_c__HeadMove_GetResult_Response_message_member_array[1].members_ =
    ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_introspection_c, interfaces, action, HeadMove_Result)();
  if (!interfaces__action__HeadMove_GetResult_Response__rosidl_typesupport_introspection_c__HeadMove_GetResult_Response_message_type_support_handle.typesupport_identifier) {
    interfaces__action__HeadMove_GetResult_Response__rosidl_typesupport_introspection_c__HeadMove_GetResult_Response_message_type_support_handle.typesupport_identifier =
      rosidl_typesupport_introspection_c__identifier;
  }
  return &interfaces__action__HeadMove_GetResult_Response__rosidl_typesupport_introspection_c__HeadMove_GetResult_Response_message_type_support_handle;
}
#ifdef __cplusplus
}
#endif

// already included above
// #include "rosidl_runtime_c/service_type_support_struct.h"
// already included above
// #include "interfaces/msg/rosidl_typesupport_introspection_c__visibility_control.h"
// already included above
// #include "interfaces/action/detail/head_move__rosidl_typesupport_introspection_c.h"
// already included above
// #include "rosidl_typesupport_introspection_c/identifier.h"
// already included above
// #include "rosidl_typesupport_introspection_c/service_introspection.h"

// this is intentionally not const to allow initialization later to prevent an initialization race
static rosidl_typesupport_introspection_c__ServiceMembers interfaces__action__detail__head_move__rosidl_typesupport_introspection_c__HeadMove_GetResult_service_members = {
  "interfaces__action",  // service namespace
  "HeadMove_GetResult",  // service name
  // these two fields are initialized below on the first access
  NULL,  // request message
  // interfaces__action__detail__head_move__rosidl_typesupport_introspection_c__HeadMove_GetResult_Request_message_type_support_handle,
  NULL  // response message
  // interfaces__action__detail__head_move__rosidl_typesupport_introspection_c__HeadMove_GetResult_Response_message_type_support_handle
};

static rosidl_service_type_support_t interfaces__action__detail__head_move__rosidl_typesupport_introspection_c__HeadMove_GetResult_service_type_support_handle = {
  0,
  &interfaces__action__detail__head_move__rosidl_typesupport_introspection_c__HeadMove_GetResult_service_members,
  get_service_typesupport_handle_function,
};

// Forward declaration of request/response type support functions
const rosidl_message_type_support_t *
ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_introspection_c, interfaces, action, HeadMove_GetResult_Request)();

const rosidl_message_type_support_t *
ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_introspection_c, interfaces, action, HeadMove_GetResult_Response)();

ROSIDL_TYPESUPPORT_INTROSPECTION_C_EXPORT_interfaces
const rosidl_service_type_support_t *
ROSIDL_TYPESUPPORT_INTERFACE__SERVICE_SYMBOL_NAME(rosidl_typesupport_introspection_c, interfaces, action, HeadMove_GetResult)() {
  if (!interfaces__action__detail__head_move__rosidl_typesupport_introspection_c__HeadMove_GetResult_service_type_support_handle.typesupport_identifier) {
    interfaces__action__detail__head_move__rosidl_typesupport_introspection_c__HeadMove_GetResult_service_type_support_handle.typesupport_identifier =
      rosidl_typesupport_introspection_c__identifier;
  }
  rosidl_typesupport_introspection_c__ServiceMembers * service_members =
    (rosidl_typesupport_introspection_c__ServiceMembers *)interfaces__action__detail__head_move__rosidl_typesupport_introspection_c__HeadMove_GetResult_service_type_support_handle.data;

  if (!service_members->request_members_) {
    service_members->request_members_ =
      (const rosidl_typesupport_introspection_c__MessageMembers *)
      ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_introspection_c, interfaces, action, HeadMove_GetResult_Request)()->data;
  }
  if (!service_members->response_members_) {
    service_members->response_members_ =
      (const rosidl_typesupport_introspection_c__MessageMembers *)
      ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_introspection_c, interfaces, action, HeadMove_GetResult_Response)()->data;
  }

  return &interfaces__action__detail__head_move__rosidl_typesupport_introspection_c__HeadMove_GetResult_service_type_support_handle;
}

// already included above
// #include <stddef.h>
// already included above
// #include "interfaces/action/detail/head_move__rosidl_typesupport_introspection_c.h"
// already included above
// #include "interfaces/msg/rosidl_typesupport_introspection_c__visibility_control.h"
// already included above
// #include "rosidl_typesupport_introspection_c/field_types.h"
// already included above
// #include "rosidl_typesupport_introspection_c/identifier.h"
// already included above
// #include "rosidl_typesupport_introspection_c/message_introspection.h"
// already included above
// #include "interfaces/action/detail/head_move__functions.h"
// already included above
// #include "interfaces/action/detail/head_move__struct.h"


// Include directives for member types
// Member `goal_id`
// already included above
// #include "unique_identifier_msgs/msg/uuid.h"
// Member `goal_id`
// already included above
// #include "unique_identifier_msgs/msg/detail/uuid__rosidl_typesupport_introspection_c.h"
// Member `feedback`
// already included above
// #include "interfaces/action/head_move.h"
// Member `feedback`
// already included above
// #include "interfaces/action/detail/head_move__rosidl_typesupport_introspection_c.h"

#ifdef __cplusplus
extern "C"
{
#endif

void interfaces__action__HeadMove_FeedbackMessage__rosidl_typesupport_introspection_c__HeadMove_FeedbackMessage_init_function(
  void * message_memory, enum rosidl_runtime_c__message_initialization _init)
{
  // TODO(karsten1987): initializers are not yet implemented for typesupport c
  // see https://github.com/ros2/ros2/issues/397
  (void) _init;
  interfaces__action__HeadMove_FeedbackMessage__init(message_memory);
}

void interfaces__action__HeadMove_FeedbackMessage__rosidl_typesupport_introspection_c__HeadMove_FeedbackMessage_fini_function(void * message_memory)
{
  interfaces__action__HeadMove_FeedbackMessage__fini(message_memory);
}

static rosidl_typesupport_introspection_c__MessageMember interfaces__action__HeadMove_FeedbackMessage__rosidl_typesupport_introspection_c__HeadMove_FeedbackMessage_message_member_array[2] = {
  {
    "goal_id",  // name
    rosidl_typesupport_introspection_c__ROS_TYPE_MESSAGE,  // type
    0,  // upper bound of string
    NULL,  // members of sub message (initialized later)
    false,  // is array
    0,  // array size
    false,  // is upper bound
    offsetof(interfaces__action__HeadMove_FeedbackMessage, goal_id),  // bytes offset in struct
    NULL,  // default value
    NULL,  // size() function pointer
    NULL,  // get_const(index) function pointer
    NULL,  // get(index) function pointer
    NULL,  // fetch(index, &value) function pointer
    NULL,  // assign(index, value) function pointer
    NULL  // resize(index) function pointer
  },
  {
    "feedback",  // name
    rosidl_typesupport_introspection_c__ROS_TYPE_MESSAGE,  // type
    0,  // upper bound of string
    NULL,  // members of sub message (initialized later)
    false,  // is array
    0,  // array size
    false,  // is upper bound
    offsetof(interfaces__action__HeadMove_FeedbackMessage, feedback),  // bytes offset in struct
    NULL,  // default value
    NULL,  // size() function pointer
    NULL,  // get_const(index) function pointer
    NULL,  // get(index) function pointer
    NULL,  // fetch(index, &value) function pointer
    NULL,  // assign(index, value) function pointer
    NULL  // resize(index) function pointer
  }
};

static const rosidl_typesupport_introspection_c__MessageMembers interfaces__action__HeadMove_FeedbackMessage__rosidl_typesupport_introspection_c__HeadMove_FeedbackMessage_message_members = {
  "interfaces__action",  // message namespace
  "HeadMove_FeedbackMessage",  // message name
  2,  // number of fields
  sizeof(interfaces__action__HeadMove_FeedbackMessage),
  interfaces__action__HeadMove_FeedbackMessage__rosidl_typesupport_introspection_c__HeadMove_FeedbackMessage_message_member_array,  // message members
  interfaces__action__HeadMove_FeedbackMessage__rosidl_typesupport_introspection_c__HeadMove_FeedbackMessage_init_function,  // function to initialize message memory (memory has to be allocated)
  interfaces__action__HeadMove_FeedbackMessage__rosidl_typesupport_introspection_c__HeadMove_FeedbackMessage_fini_function  // function to terminate message instance (will not free memory)
};

// this is not const since it must be initialized on first access
// since C does not allow non-integral compile-time constants
static rosidl_message_type_support_t interfaces__action__HeadMove_FeedbackMessage__rosidl_typesupport_introspection_c__HeadMove_FeedbackMessage_message_type_support_handle = {
  0,
  &interfaces__action__HeadMove_FeedbackMessage__rosidl_typesupport_introspection_c__HeadMove_FeedbackMessage_message_members,
  get_message_typesupport_handle_function,
};

ROSIDL_TYPESUPPORT_INTROSPECTION_C_EXPORT_interfaces
const rosidl_message_type_support_t *
ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_introspection_c, interfaces, action, HeadMove_FeedbackMessage)() {
  interfaces__action__HeadMove_FeedbackMessage__rosidl_typesupport_introspection_c__HeadMove_FeedbackMessage_message_member_array[0].members_ =
    ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_introspection_c, unique_identifier_msgs, msg, UUID)();
  interfaces__action__HeadMove_FeedbackMessage__rosidl_typesupport_introspection_c__HeadMove_FeedbackMessage_message_member_array[1].members_ =
    ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_introspection_c, interfaces, action, HeadMove_Feedback)();
  if (!interfaces__action__HeadMove_FeedbackMessage__rosidl_typesupport_introspection_c__HeadMove_FeedbackMessage_message_type_support_handle.typesupport_identifier) {
    interfaces__action__HeadMove_FeedbackMessage__rosidl_typesupport_introspection_c__HeadMove_FeedbackMessage_message_type_support_handle.typesupport_identifier =
      rosidl_typesupport_introspection_c__identifier;
  }
  return &interfaces__action__HeadMove_FeedbackMessage__rosidl_typesupport_introspection_c__HeadMove_FeedbackMessage_message_type_support_handle;
}
#ifdef __cplusplus
}
#endif
