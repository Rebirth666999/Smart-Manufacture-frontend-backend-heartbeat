// generated from rosidl_generator_c/resource/idl__struct.h.em
// with input from interfaces:action/BeltMove.idl
// generated code does not contain a copyright notice

#ifndef INTERFACES__ACTION__DETAIL__BELT_MOVE__STRUCT_H_
#define INTERFACES__ACTION__DETAIL__BELT_MOVE__STRUCT_H_

#ifdef __cplusplus
extern "C"
{
#endif

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>


// Constants defined in the message

/// Struct defined in action/BeltMove in the package interfaces.
typedef struct interfaces__action__BeltMove_Goal
{
  uint8_t structure_needs_at_least_one_member;
} interfaces__action__BeltMove_Goal;

// Struct for a sequence of interfaces__action__BeltMove_Goal.
typedef struct interfaces__action__BeltMove_Goal__Sequence
{
  interfaces__action__BeltMove_Goal * data;
  /// The number of valid items in data
  size_t size;
  /// The number of allocated items in data
  size_t capacity;
} interfaces__action__BeltMove_Goal__Sequence;


// Constants defined in the message

// Include directives for member types
// Member 'finish'
#include "rosidl_runtime_c/string.h"

/// Struct defined in action/BeltMove in the package interfaces.
typedef struct interfaces__action__BeltMove_Result
{
  rosidl_runtime_c__String finish;
} interfaces__action__BeltMove_Result;

// Struct for a sequence of interfaces__action__BeltMove_Result.
typedef struct interfaces__action__BeltMove_Result__Sequence
{
  interfaces__action__BeltMove_Result * data;
  /// The number of valid items in data
  size_t size;
  /// The number of allocated items in data
  size_t capacity;
} interfaces__action__BeltMove_Result__Sequence;


// Constants defined in the message

/// Struct defined in action/BeltMove in the package interfaces.
typedef struct interfaces__action__BeltMove_Feedback
{
  int32_t feedback;
} interfaces__action__BeltMove_Feedback;

// Struct for a sequence of interfaces__action__BeltMove_Feedback.
typedef struct interfaces__action__BeltMove_Feedback__Sequence
{
  interfaces__action__BeltMove_Feedback * data;
  /// The number of valid items in data
  size_t size;
  /// The number of allocated items in data
  size_t capacity;
} interfaces__action__BeltMove_Feedback__Sequence;


// Constants defined in the message

// Include directives for member types
// Member 'goal_id'
#include "unique_identifier_msgs/msg/detail/uuid__struct.h"
// Member 'goal'
#include "interfaces/action/detail/belt_move__struct.h"

/// Struct defined in action/BeltMove in the package interfaces.
typedef struct interfaces__action__BeltMove_SendGoal_Request
{
  unique_identifier_msgs__msg__UUID goal_id;
  interfaces__action__BeltMove_Goal goal;
} interfaces__action__BeltMove_SendGoal_Request;

// Struct for a sequence of interfaces__action__BeltMove_SendGoal_Request.
typedef struct interfaces__action__BeltMove_SendGoal_Request__Sequence
{
  interfaces__action__BeltMove_SendGoal_Request * data;
  /// The number of valid items in data
  size_t size;
  /// The number of allocated items in data
  size_t capacity;
} interfaces__action__BeltMove_SendGoal_Request__Sequence;


// Constants defined in the message

// Include directives for member types
// Member 'stamp'
#include "builtin_interfaces/msg/detail/time__struct.h"

/// Struct defined in action/BeltMove in the package interfaces.
typedef struct interfaces__action__BeltMove_SendGoal_Response
{
  bool accepted;
  builtin_interfaces__msg__Time stamp;
} interfaces__action__BeltMove_SendGoal_Response;

// Struct for a sequence of interfaces__action__BeltMove_SendGoal_Response.
typedef struct interfaces__action__BeltMove_SendGoal_Response__Sequence
{
  interfaces__action__BeltMove_SendGoal_Response * data;
  /// The number of valid items in data
  size_t size;
  /// The number of allocated items in data
  size_t capacity;
} interfaces__action__BeltMove_SendGoal_Response__Sequence;


// Constants defined in the message

// Include directives for member types
// Member 'goal_id'
// already included above
// #include "unique_identifier_msgs/msg/detail/uuid__struct.h"

/// Struct defined in action/BeltMove in the package interfaces.
typedef struct interfaces__action__BeltMove_GetResult_Request
{
  unique_identifier_msgs__msg__UUID goal_id;
} interfaces__action__BeltMove_GetResult_Request;

// Struct for a sequence of interfaces__action__BeltMove_GetResult_Request.
typedef struct interfaces__action__BeltMove_GetResult_Request__Sequence
{
  interfaces__action__BeltMove_GetResult_Request * data;
  /// The number of valid items in data
  size_t size;
  /// The number of allocated items in data
  size_t capacity;
} interfaces__action__BeltMove_GetResult_Request__Sequence;


// Constants defined in the message

// Include directives for member types
// Member 'result'
// already included above
// #include "interfaces/action/detail/belt_move__struct.h"

/// Struct defined in action/BeltMove in the package interfaces.
typedef struct interfaces__action__BeltMove_GetResult_Response
{
  int8_t status;
  interfaces__action__BeltMove_Result result;
} interfaces__action__BeltMove_GetResult_Response;

// Struct for a sequence of interfaces__action__BeltMove_GetResult_Response.
typedef struct interfaces__action__BeltMove_GetResult_Response__Sequence
{
  interfaces__action__BeltMove_GetResult_Response * data;
  /// The number of valid items in data
  size_t size;
  /// The number of allocated items in data
  size_t capacity;
} interfaces__action__BeltMove_GetResult_Response__Sequence;


// Constants defined in the message

// Include directives for member types
// Member 'goal_id'
// already included above
// #include "unique_identifier_msgs/msg/detail/uuid__struct.h"
// Member 'feedback'
// already included above
// #include "interfaces/action/detail/belt_move__struct.h"

/// Struct defined in action/BeltMove in the package interfaces.
typedef struct interfaces__action__BeltMove_FeedbackMessage
{
  unique_identifier_msgs__msg__UUID goal_id;
  interfaces__action__BeltMove_Feedback feedback;
} interfaces__action__BeltMove_FeedbackMessage;

// Struct for a sequence of interfaces__action__BeltMove_FeedbackMessage.
typedef struct interfaces__action__BeltMove_FeedbackMessage__Sequence
{
  interfaces__action__BeltMove_FeedbackMessage * data;
  /// The number of valid items in data
  size_t size;
  /// The number of allocated items in data
  size_t capacity;
} interfaces__action__BeltMove_FeedbackMessage__Sequence;

#ifdef __cplusplus
}
#endif

#endif  // INTERFACES__ACTION__DETAIL__BELT_MOVE__STRUCT_H_
