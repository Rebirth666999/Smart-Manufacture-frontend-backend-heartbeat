// generated from rosidl_generator_c/resource/idl__struct.h.em
// with input from interfaces:action/Fetch.idl
// generated code does not contain a copyright notice

#ifndef INTERFACES__ACTION__DETAIL__FETCH__STRUCT_H_
#define INTERFACES__ACTION__DETAIL__FETCH__STRUCT_H_

#ifdef __cplusplus
extern "C"
{
#endif

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>


// Constants defined in the message

// Include directives for member types
// Member 'para'
#include "rosidl_runtime_c/primitives_sequence.h"
// Member 'lst'
#include "rosidl_runtime_c/string.h"

/// Struct defined in action/Fetch in the package interfaces.
typedef struct interfaces__action__Fetch_Goal
{
  rosidl_runtime_c__double__Sequence para;
  rosidl_runtime_c__String lst;
} interfaces__action__Fetch_Goal;

// Struct for a sequence of interfaces__action__Fetch_Goal.
typedef struct interfaces__action__Fetch_Goal__Sequence
{
  interfaces__action__Fetch_Goal * data;
  /// The number of valid items in data
  size_t size;
  /// The number of allocated items in data
  size_t capacity;
} interfaces__action__Fetch_Goal__Sequence;


// Constants defined in the message

// Include directives for member types
// Member 'finish'
// already included above
// #include "rosidl_runtime_c/string.h"

/// Struct defined in action/Fetch in the package interfaces.
typedef struct interfaces__action__Fetch_Result
{
  rosidl_runtime_c__String finish;
} interfaces__action__Fetch_Result;

// Struct for a sequence of interfaces__action__Fetch_Result.
typedef struct interfaces__action__Fetch_Result__Sequence
{
  interfaces__action__Fetch_Result * data;
  /// The number of valid items in data
  size_t size;
  /// The number of allocated items in data
  size_t capacity;
} interfaces__action__Fetch_Result__Sequence;


// Constants defined in the message

// Include directives for member types
// Member 'feedback'
// already included above
// #include "rosidl_runtime_c/string.h"

/// Struct defined in action/Fetch in the package interfaces.
typedef struct interfaces__action__Fetch_Feedback
{
  rosidl_runtime_c__String feedback;
} interfaces__action__Fetch_Feedback;

// Struct for a sequence of interfaces__action__Fetch_Feedback.
typedef struct interfaces__action__Fetch_Feedback__Sequence
{
  interfaces__action__Fetch_Feedback * data;
  /// The number of valid items in data
  size_t size;
  /// The number of allocated items in data
  size_t capacity;
} interfaces__action__Fetch_Feedback__Sequence;


// Constants defined in the message

// Include directives for member types
// Member 'goal_id'
#include "unique_identifier_msgs/msg/detail/uuid__struct.h"
// Member 'goal'
#include "interfaces/action/detail/fetch__struct.h"

/// Struct defined in action/Fetch in the package interfaces.
typedef struct interfaces__action__Fetch_SendGoal_Request
{
  unique_identifier_msgs__msg__UUID goal_id;
  interfaces__action__Fetch_Goal goal;
} interfaces__action__Fetch_SendGoal_Request;

// Struct for a sequence of interfaces__action__Fetch_SendGoal_Request.
typedef struct interfaces__action__Fetch_SendGoal_Request__Sequence
{
  interfaces__action__Fetch_SendGoal_Request * data;
  /// The number of valid items in data
  size_t size;
  /// The number of allocated items in data
  size_t capacity;
} interfaces__action__Fetch_SendGoal_Request__Sequence;


// Constants defined in the message

// Include directives for member types
// Member 'stamp'
#include "builtin_interfaces/msg/detail/time__struct.h"

/// Struct defined in action/Fetch in the package interfaces.
typedef struct interfaces__action__Fetch_SendGoal_Response
{
  bool accepted;
  builtin_interfaces__msg__Time stamp;
} interfaces__action__Fetch_SendGoal_Response;

// Struct for a sequence of interfaces__action__Fetch_SendGoal_Response.
typedef struct interfaces__action__Fetch_SendGoal_Response__Sequence
{
  interfaces__action__Fetch_SendGoal_Response * data;
  /// The number of valid items in data
  size_t size;
  /// The number of allocated items in data
  size_t capacity;
} interfaces__action__Fetch_SendGoal_Response__Sequence;


// Constants defined in the message

// Include directives for member types
// Member 'goal_id'
// already included above
// #include "unique_identifier_msgs/msg/detail/uuid__struct.h"

/// Struct defined in action/Fetch in the package interfaces.
typedef struct interfaces__action__Fetch_GetResult_Request
{
  unique_identifier_msgs__msg__UUID goal_id;
} interfaces__action__Fetch_GetResult_Request;

// Struct for a sequence of interfaces__action__Fetch_GetResult_Request.
typedef struct interfaces__action__Fetch_GetResult_Request__Sequence
{
  interfaces__action__Fetch_GetResult_Request * data;
  /// The number of valid items in data
  size_t size;
  /// The number of allocated items in data
  size_t capacity;
} interfaces__action__Fetch_GetResult_Request__Sequence;


// Constants defined in the message

// Include directives for member types
// Member 'result'
// already included above
// #include "interfaces/action/detail/fetch__struct.h"

/// Struct defined in action/Fetch in the package interfaces.
typedef struct interfaces__action__Fetch_GetResult_Response
{
  int8_t status;
  interfaces__action__Fetch_Result result;
} interfaces__action__Fetch_GetResult_Response;

// Struct for a sequence of interfaces__action__Fetch_GetResult_Response.
typedef struct interfaces__action__Fetch_GetResult_Response__Sequence
{
  interfaces__action__Fetch_GetResult_Response * data;
  /// The number of valid items in data
  size_t size;
  /// The number of allocated items in data
  size_t capacity;
} interfaces__action__Fetch_GetResult_Response__Sequence;


// Constants defined in the message

// Include directives for member types
// Member 'goal_id'
// already included above
// #include "unique_identifier_msgs/msg/detail/uuid__struct.h"
// Member 'feedback'
// already included above
// #include "interfaces/action/detail/fetch__struct.h"

/// Struct defined in action/Fetch in the package interfaces.
typedef struct interfaces__action__Fetch_FeedbackMessage
{
  unique_identifier_msgs__msg__UUID goal_id;
  interfaces__action__Fetch_Feedback feedback;
} interfaces__action__Fetch_FeedbackMessage;

// Struct for a sequence of interfaces__action__Fetch_FeedbackMessage.
typedef struct interfaces__action__Fetch_FeedbackMessage__Sequence
{
  interfaces__action__Fetch_FeedbackMessage * data;
  /// The number of valid items in data
  size_t size;
  /// The number of allocated items in data
  size_t capacity;
} interfaces__action__Fetch_FeedbackMessage__Sequence;

#ifdef __cplusplus
}
#endif

#endif  // INTERFACES__ACTION__DETAIL__FETCH__STRUCT_H_
