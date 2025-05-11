// generated from rosidl_typesupport_c/resource/idl__type_support.cpp.em
// with input from interfaces:action/GetStatus.idl
// generated code does not contain a copyright notice

#include "cstddef"
#include "rosidl_runtime_c/message_type_support_struct.h"
#include "interfaces/action/detail/get_status__struct.h"
#include "interfaces/action/detail/get_status__type_support.h"
#include "rosidl_typesupport_c/identifier.h"
#include "rosidl_typesupport_c/message_type_support_dispatch.h"
#include "rosidl_typesupport_c/type_support_map.h"
#include "rosidl_typesupport_c/visibility_control.h"
#include "rosidl_typesupport_interface/macros.h"

namespace interfaces
{

namespace action
{

namespace rosidl_typesupport_c
{

typedef struct _GetStatus_Goal_type_support_ids_t
{
  const char * typesupport_identifier[2];
} _GetStatus_Goal_type_support_ids_t;

static const _GetStatus_Goal_type_support_ids_t _GetStatus_Goal_message_typesupport_ids = {
  {
    "rosidl_typesupport_fastrtps_c",  // ::rosidl_typesupport_fastrtps_c::typesupport_identifier,
    "rosidl_typesupport_introspection_c",  // ::rosidl_typesupport_introspection_c::typesupport_identifier,
  }
};

typedef struct _GetStatus_Goal_type_support_symbol_names_t
{
  const char * symbol_name[2];
} _GetStatus_Goal_type_support_symbol_names_t;

#define STRINGIFY_(s) #s
#define STRINGIFY(s) STRINGIFY_(s)

static const _GetStatus_Goal_type_support_symbol_names_t _GetStatus_Goal_message_typesupport_symbol_names = {
  {
    STRINGIFY(ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_fastrtps_c, interfaces, action, GetStatus_Goal)),
    STRINGIFY(ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_introspection_c, interfaces, action, GetStatus_Goal)),
  }
};

typedef struct _GetStatus_Goal_type_support_data_t
{
  void * data[2];
} _GetStatus_Goal_type_support_data_t;

static _GetStatus_Goal_type_support_data_t _GetStatus_Goal_message_typesupport_data = {
  {
    0,  // will store the shared library later
    0,  // will store the shared library later
  }
};

static const type_support_map_t _GetStatus_Goal_message_typesupport_map = {
  2,
  "interfaces",
  &_GetStatus_Goal_message_typesupport_ids.typesupport_identifier[0],
  &_GetStatus_Goal_message_typesupport_symbol_names.symbol_name[0],
  &_GetStatus_Goal_message_typesupport_data.data[0],
};

static const rosidl_message_type_support_t GetStatus_Goal_message_type_support_handle = {
  rosidl_typesupport_c__typesupport_identifier,
  reinterpret_cast<const type_support_map_t *>(&_GetStatus_Goal_message_typesupport_map),
  rosidl_typesupport_c__get_message_typesupport_handle_function,
};

}  // namespace rosidl_typesupport_c

}  // namespace action

}  // namespace interfaces

#ifdef __cplusplus
extern "C"
{
#endif

const rosidl_message_type_support_t *
ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_c, interfaces, action, GetStatus_Goal)() {
  return &::interfaces::action::rosidl_typesupport_c::GetStatus_Goal_message_type_support_handle;
}

#ifdef __cplusplus
}
#endif

// already included above
// #include "cstddef"
// already included above
// #include "rosidl_runtime_c/message_type_support_struct.h"
// already included above
// #include "interfaces/action/detail/get_status__struct.h"
// already included above
// #include "interfaces/action/detail/get_status__type_support.h"
// already included above
// #include "rosidl_typesupport_c/identifier.h"
// already included above
// #include "rosidl_typesupport_c/message_type_support_dispatch.h"
// already included above
// #include "rosidl_typesupport_c/type_support_map.h"
// already included above
// #include "rosidl_typesupport_c/visibility_control.h"
// already included above
// #include "rosidl_typesupport_interface/macros.h"

namespace interfaces
{

namespace action
{

namespace rosidl_typesupport_c
{

typedef struct _GetStatus_Result_type_support_ids_t
{
  const char * typesupport_identifier[2];
} _GetStatus_Result_type_support_ids_t;

static const _GetStatus_Result_type_support_ids_t _GetStatus_Result_message_typesupport_ids = {
  {
    "rosidl_typesupport_fastrtps_c",  // ::rosidl_typesupport_fastrtps_c::typesupport_identifier,
    "rosidl_typesupport_introspection_c",  // ::rosidl_typesupport_introspection_c::typesupport_identifier,
  }
};

typedef struct _GetStatus_Result_type_support_symbol_names_t
{
  const char * symbol_name[2];
} _GetStatus_Result_type_support_symbol_names_t;

#define STRINGIFY_(s) #s
#define STRINGIFY(s) STRINGIFY_(s)

static const _GetStatus_Result_type_support_symbol_names_t _GetStatus_Result_message_typesupport_symbol_names = {
  {
    STRINGIFY(ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_fastrtps_c, interfaces, action, GetStatus_Result)),
    STRINGIFY(ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_introspection_c, interfaces, action, GetStatus_Result)),
  }
};

typedef struct _GetStatus_Result_type_support_data_t
{
  void * data[2];
} _GetStatus_Result_type_support_data_t;

static _GetStatus_Result_type_support_data_t _GetStatus_Result_message_typesupport_data = {
  {
    0,  // will store the shared library later
    0,  // will store the shared library later
  }
};

static const type_support_map_t _GetStatus_Result_message_typesupport_map = {
  2,
  "interfaces",
  &_GetStatus_Result_message_typesupport_ids.typesupport_identifier[0],
  &_GetStatus_Result_message_typesupport_symbol_names.symbol_name[0],
  &_GetStatus_Result_message_typesupport_data.data[0],
};

static const rosidl_message_type_support_t GetStatus_Result_message_type_support_handle = {
  rosidl_typesupport_c__typesupport_identifier,
  reinterpret_cast<const type_support_map_t *>(&_GetStatus_Result_message_typesupport_map),
  rosidl_typesupport_c__get_message_typesupport_handle_function,
};

}  // namespace rosidl_typesupport_c

}  // namespace action

}  // namespace interfaces

#ifdef __cplusplus
extern "C"
{
#endif

const rosidl_message_type_support_t *
ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_c, interfaces, action, GetStatus_Result)() {
  return &::interfaces::action::rosidl_typesupport_c::GetStatus_Result_message_type_support_handle;
}

#ifdef __cplusplus
}
#endif

// already included above
// #include "cstddef"
// already included above
// #include "rosidl_runtime_c/message_type_support_struct.h"
// already included above
// #include "interfaces/action/detail/get_status__struct.h"
// already included above
// #include "interfaces/action/detail/get_status__type_support.h"
// already included above
// #include "rosidl_typesupport_c/identifier.h"
// already included above
// #include "rosidl_typesupport_c/message_type_support_dispatch.h"
// already included above
// #include "rosidl_typesupport_c/type_support_map.h"
// already included above
// #include "rosidl_typesupport_c/visibility_control.h"
// already included above
// #include "rosidl_typesupport_interface/macros.h"

namespace interfaces
{

namespace action
{

namespace rosidl_typesupport_c
{

typedef struct _GetStatus_Feedback_type_support_ids_t
{
  const char * typesupport_identifier[2];
} _GetStatus_Feedback_type_support_ids_t;

static const _GetStatus_Feedback_type_support_ids_t _GetStatus_Feedback_message_typesupport_ids = {
  {
    "rosidl_typesupport_fastrtps_c",  // ::rosidl_typesupport_fastrtps_c::typesupport_identifier,
    "rosidl_typesupport_introspection_c",  // ::rosidl_typesupport_introspection_c::typesupport_identifier,
  }
};

typedef struct _GetStatus_Feedback_type_support_symbol_names_t
{
  const char * symbol_name[2];
} _GetStatus_Feedback_type_support_symbol_names_t;

#define STRINGIFY_(s) #s
#define STRINGIFY(s) STRINGIFY_(s)

static const _GetStatus_Feedback_type_support_symbol_names_t _GetStatus_Feedback_message_typesupport_symbol_names = {
  {
    STRINGIFY(ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_fastrtps_c, interfaces, action, GetStatus_Feedback)),
    STRINGIFY(ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_introspection_c, interfaces, action, GetStatus_Feedback)),
  }
};

typedef struct _GetStatus_Feedback_type_support_data_t
{
  void * data[2];
} _GetStatus_Feedback_type_support_data_t;

static _GetStatus_Feedback_type_support_data_t _GetStatus_Feedback_message_typesupport_data = {
  {
    0,  // will store the shared library later
    0,  // will store the shared library later
  }
};

static const type_support_map_t _GetStatus_Feedback_message_typesupport_map = {
  2,
  "interfaces",
  &_GetStatus_Feedback_message_typesupport_ids.typesupport_identifier[0],
  &_GetStatus_Feedback_message_typesupport_symbol_names.symbol_name[0],
  &_GetStatus_Feedback_message_typesupport_data.data[0],
};

static const rosidl_message_type_support_t GetStatus_Feedback_message_type_support_handle = {
  rosidl_typesupport_c__typesupport_identifier,
  reinterpret_cast<const type_support_map_t *>(&_GetStatus_Feedback_message_typesupport_map),
  rosidl_typesupport_c__get_message_typesupport_handle_function,
};

}  // namespace rosidl_typesupport_c

}  // namespace action

}  // namespace interfaces

#ifdef __cplusplus
extern "C"
{
#endif

const rosidl_message_type_support_t *
ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_c, interfaces, action, GetStatus_Feedback)() {
  return &::interfaces::action::rosidl_typesupport_c::GetStatus_Feedback_message_type_support_handle;
}

#ifdef __cplusplus
}
#endif

// already included above
// #include "cstddef"
// already included above
// #include "rosidl_runtime_c/message_type_support_struct.h"
// already included above
// #include "interfaces/action/detail/get_status__struct.h"
// already included above
// #include "interfaces/action/detail/get_status__type_support.h"
// already included above
// #include "rosidl_typesupport_c/identifier.h"
// already included above
// #include "rosidl_typesupport_c/message_type_support_dispatch.h"
// already included above
// #include "rosidl_typesupport_c/type_support_map.h"
// already included above
// #include "rosidl_typesupport_c/visibility_control.h"
// already included above
// #include "rosidl_typesupport_interface/macros.h"

namespace interfaces
{

namespace action
{

namespace rosidl_typesupport_c
{

typedef struct _GetStatus_SendGoal_Request_type_support_ids_t
{
  const char * typesupport_identifier[2];
} _GetStatus_SendGoal_Request_type_support_ids_t;

static const _GetStatus_SendGoal_Request_type_support_ids_t _GetStatus_SendGoal_Request_message_typesupport_ids = {
  {
    "rosidl_typesupport_fastrtps_c",  // ::rosidl_typesupport_fastrtps_c::typesupport_identifier,
    "rosidl_typesupport_introspection_c",  // ::rosidl_typesupport_introspection_c::typesupport_identifier,
  }
};

typedef struct _GetStatus_SendGoal_Request_type_support_symbol_names_t
{
  const char * symbol_name[2];
} _GetStatus_SendGoal_Request_type_support_symbol_names_t;

#define STRINGIFY_(s) #s
#define STRINGIFY(s) STRINGIFY_(s)

static const _GetStatus_SendGoal_Request_type_support_symbol_names_t _GetStatus_SendGoal_Request_message_typesupport_symbol_names = {
  {
    STRINGIFY(ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_fastrtps_c, interfaces, action, GetStatus_SendGoal_Request)),
    STRINGIFY(ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_introspection_c, interfaces, action, GetStatus_SendGoal_Request)),
  }
};

typedef struct _GetStatus_SendGoal_Request_type_support_data_t
{
  void * data[2];
} _GetStatus_SendGoal_Request_type_support_data_t;

static _GetStatus_SendGoal_Request_type_support_data_t _GetStatus_SendGoal_Request_message_typesupport_data = {
  {
    0,  // will store the shared library later
    0,  // will store the shared library later
  }
};

static const type_support_map_t _GetStatus_SendGoal_Request_message_typesupport_map = {
  2,
  "interfaces",
  &_GetStatus_SendGoal_Request_message_typesupport_ids.typesupport_identifier[0],
  &_GetStatus_SendGoal_Request_message_typesupport_symbol_names.symbol_name[0],
  &_GetStatus_SendGoal_Request_message_typesupport_data.data[0],
};

static const rosidl_message_type_support_t GetStatus_SendGoal_Request_message_type_support_handle = {
  rosidl_typesupport_c__typesupport_identifier,
  reinterpret_cast<const type_support_map_t *>(&_GetStatus_SendGoal_Request_message_typesupport_map),
  rosidl_typesupport_c__get_message_typesupport_handle_function,
};

}  // namespace rosidl_typesupport_c

}  // namespace action

}  // namespace interfaces

#ifdef __cplusplus
extern "C"
{
#endif

const rosidl_message_type_support_t *
ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_c, interfaces, action, GetStatus_SendGoal_Request)() {
  return &::interfaces::action::rosidl_typesupport_c::GetStatus_SendGoal_Request_message_type_support_handle;
}

#ifdef __cplusplus
}
#endif

// already included above
// #include "cstddef"
// already included above
// #include "rosidl_runtime_c/message_type_support_struct.h"
// already included above
// #include "interfaces/action/detail/get_status__struct.h"
// already included above
// #include "interfaces/action/detail/get_status__type_support.h"
// already included above
// #include "rosidl_typesupport_c/identifier.h"
// already included above
// #include "rosidl_typesupport_c/message_type_support_dispatch.h"
// already included above
// #include "rosidl_typesupport_c/type_support_map.h"
// already included above
// #include "rosidl_typesupport_c/visibility_control.h"
// already included above
// #include "rosidl_typesupport_interface/macros.h"

namespace interfaces
{

namespace action
{

namespace rosidl_typesupport_c
{

typedef struct _GetStatus_SendGoal_Response_type_support_ids_t
{
  const char * typesupport_identifier[2];
} _GetStatus_SendGoal_Response_type_support_ids_t;

static const _GetStatus_SendGoal_Response_type_support_ids_t _GetStatus_SendGoal_Response_message_typesupport_ids = {
  {
    "rosidl_typesupport_fastrtps_c",  // ::rosidl_typesupport_fastrtps_c::typesupport_identifier,
    "rosidl_typesupport_introspection_c",  // ::rosidl_typesupport_introspection_c::typesupport_identifier,
  }
};

typedef struct _GetStatus_SendGoal_Response_type_support_symbol_names_t
{
  const char * symbol_name[2];
} _GetStatus_SendGoal_Response_type_support_symbol_names_t;

#define STRINGIFY_(s) #s
#define STRINGIFY(s) STRINGIFY_(s)

static const _GetStatus_SendGoal_Response_type_support_symbol_names_t _GetStatus_SendGoal_Response_message_typesupport_symbol_names = {
  {
    STRINGIFY(ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_fastrtps_c, interfaces, action, GetStatus_SendGoal_Response)),
    STRINGIFY(ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_introspection_c, interfaces, action, GetStatus_SendGoal_Response)),
  }
};

typedef struct _GetStatus_SendGoal_Response_type_support_data_t
{
  void * data[2];
} _GetStatus_SendGoal_Response_type_support_data_t;

static _GetStatus_SendGoal_Response_type_support_data_t _GetStatus_SendGoal_Response_message_typesupport_data = {
  {
    0,  // will store the shared library later
    0,  // will store the shared library later
  }
};

static const type_support_map_t _GetStatus_SendGoal_Response_message_typesupport_map = {
  2,
  "interfaces",
  &_GetStatus_SendGoal_Response_message_typesupport_ids.typesupport_identifier[0],
  &_GetStatus_SendGoal_Response_message_typesupport_symbol_names.symbol_name[0],
  &_GetStatus_SendGoal_Response_message_typesupport_data.data[0],
};

static const rosidl_message_type_support_t GetStatus_SendGoal_Response_message_type_support_handle = {
  rosidl_typesupport_c__typesupport_identifier,
  reinterpret_cast<const type_support_map_t *>(&_GetStatus_SendGoal_Response_message_typesupport_map),
  rosidl_typesupport_c__get_message_typesupport_handle_function,
};

}  // namespace rosidl_typesupport_c

}  // namespace action

}  // namespace interfaces

#ifdef __cplusplus
extern "C"
{
#endif

const rosidl_message_type_support_t *
ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_c, interfaces, action, GetStatus_SendGoal_Response)() {
  return &::interfaces::action::rosidl_typesupport_c::GetStatus_SendGoal_Response_message_type_support_handle;
}

#ifdef __cplusplus
}
#endif

// already included above
// #include "cstddef"
#include "rosidl_runtime_c/service_type_support_struct.h"
// already included above
// #include "interfaces/action/detail/get_status__type_support.h"
// already included above
// #include "rosidl_typesupport_c/identifier.h"
#include "rosidl_typesupport_c/service_type_support_dispatch.h"
// already included above
// #include "rosidl_typesupport_c/type_support_map.h"
// already included above
// #include "rosidl_typesupport_interface/macros.h"

namespace interfaces
{

namespace action
{

namespace rosidl_typesupport_c
{

typedef struct _GetStatus_SendGoal_type_support_ids_t
{
  const char * typesupport_identifier[2];
} _GetStatus_SendGoal_type_support_ids_t;

static const _GetStatus_SendGoal_type_support_ids_t _GetStatus_SendGoal_service_typesupport_ids = {
  {
    "rosidl_typesupport_fastrtps_c",  // ::rosidl_typesupport_fastrtps_c::typesupport_identifier,
    "rosidl_typesupport_introspection_c",  // ::rosidl_typesupport_introspection_c::typesupport_identifier,
  }
};

typedef struct _GetStatus_SendGoal_type_support_symbol_names_t
{
  const char * symbol_name[2];
} _GetStatus_SendGoal_type_support_symbol_names_t;

#define STRINGIFY_(s) #s
#define STRINGIFY(s) STRINGIFY_(s)

static const _GetStatus_SendGoal_type_support_symbol_names_t _GetStatus_SendGoal_service_typesupport_symbol_names = {
  {
    STRINGIFY(ROSIDL_TYPESUPPORT_INTERFACE__SERVICE_SYMBOL_NAME(rosidl_typesupport_fastrtps_c, interfaces, action, GetStatus_SendGoal)),
    STRINGIFY(ROSIDL_TYPESUPPORT_INTERFACE__SERVICE_SYMBOL_NAME(rosidl_typesupport_introspection_c, interfaces, action, GetStatus_SendGoal)),
  }
};

typedef struct _GetStatus_SendGoal_type_support_data_t
{
  void * data[2];
} _GetStatus_SendGoal_type_support_data_t;

static _GetStatus_SendGoal_type_support_data_t _GetStatus_SendGoal_service_typesupport_data = {
  {
    0,  // will store the shared library later
    0,  // will store the shared library later
  }
};

static const type_support_map_t _GetStatus_SendGoal_service_typesupport_map = {
  2,
  "interfaces",
  &_GetStatus_SendGoal_service_typesupport_ids.typesupport_identifier[0],
  &_GetStatus_SendGoal_service_typesupport_symbol_names.symbol_name[0],
  &_GetStatus_SendGoal_service_typesupport_data.data[0],
};

static const rosidl_service_type_support_t GetStatus_SendGoal_service_type_support_handle = {
  rosidl_typesupport_c__typesupport_identifier,
  reinterpret_cast<const type_support_map_t *>(&_GetStatus_SendGoal_service_typesupport_map),
  rosidl_typesupport_c__get_service_typesupport_handle_function,
};

}  // namespace rosidl_typesupport_c

}  // namespace action

}  // namespace interfaces

#ifdef __cplusplus
extern "C"
{
#endif

const rosidl_service_type_support_t *
ROSIDL_TYPESUPPORT_INTERFACE__SERVICE_SYMBOL_NAME(rosidl_typesupport_c, interfaces, action, GetStatus_SendGoal)() {
  return &::interfaces::action::rosidl_typesupport_c::GetStatus_SendGoal_service_type_support_handle;
}

#ifdef __cplusplus
}
#endif

// already included above
// #include "cstddef"
// already included above
// #include "rosidl_runtime_c/message_type_support_struct.h"
// already included above
// #include "interfaces/action/detail/get_status__struct.h"
// already included above
// #include "interfaces/action/detail/get_status__type_support.h"
// already included above
// #include "rosidl_typesupport_c/identifier.h"
// already included above
// #include "rosidl_typesupport_c/message_type_support_dispatch.h"
// already included above
// #include "rosidl_typesupport_c/type_support_map.h"
// already included above
// #include "rosidl_typesupport_c/visibility_control.h"
// already included above
// #include "rosidl_typesupport_interface/macros.h"

namespace interfaces
{

namespace action
{

namespace rosidl_typesupport_c
{

typedef struct _GetStatus_GetResult_Request_type_support_ids_t
{
  const char * typesupport_identifier[2];
} _GetStatus_GetResult_Request_type_support_ids_t;

static const _GetStatus_GetResult_Request_type_support_ids_t _GetStatus_GetResult_Request_message_typesupport_ids = {
  {
    "rosidl_typesupport_fastrtps_c",  // ::rosidl_typesupport_fastrtps_c::typesupport_identifier,
    "rosidl_typesupport_introspection_c",  // ::rosidl_typesupport_introspection_c::typesupport_identifier,
  }
};

typedef struct _GetStatus_GetResult_Request_type_support_symbol_names_t
{
  const char * symbol_name[2];
} _GetStatus_GetResult_Request_type_support_symbol_names_t;

#define STRINGIFY_(s) #s
#define STRINGIFY(s) STRINGIFY_(s)

static const _GetStatus_GetResult_Request_type_support_symbol_names_t _GetStatus_GetResult_Request_message_typesupport_symbol_names = {
  {
    STRINGIFY(ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_fastrtps_c, interfaces, action, GetStatus_GetResult_Request)),
    STRINGIFY(ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_introspection_c, interfaces, action, GetStatus_GetResult_Request)),
  }
};

typedef struct _GetStatus_GetResult_Request_type_support_data_t
{
  void * data[2];
} _GetStatus_GetResult_Request_type_support_data_t;

static _GetStatus_GetResult_Request_type_support_data_t _GetStatus_GetResult_Request_message_typesupport_data = {
  {
    0,  // will store the shared library later
    0,  // will store the shared library later
  }
};

static const type_support_map_t _GetStatus_GetResult_Request_message_typesupport_map = {
  2,
  "interfaces",
  &_GetStatus_GetResult_Request_message_typesupport_ids.typesupport_identifier[0],
  &_GetStatus_GetResult_Request_message_typesupport_symbol_names.symbol_name[0],
  &_GetStatus_GetResult_Request_message_typesupport_data.data[0],
};

static const rosidl_message_type_support_t GetStatus_GetResult_Request_message_type_support_handle = {
  rosidl_typesupport_c__typesupport_identifier,
  reinterpret_cast<const type_support_map_t *>(&_GetStatus_GetResult_Request_message_typesupport_map),
  rosidl_typesupport_c__get_message_typesupport_handle_function,
};

}  // namespace rosidl_typesupport_c

}  // namespace action

}  // namespace interfaces

#ifdef __cplusplus
extern "C"
{
#endif

const rosidl_message_type_support_t *
ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_c, interfaces, action, GetStatus_GetResult_Request)() {
  return &::interfaces::action::rosidl_typesupport_c::GetStatus_GetResult_Request_message_type_support_handle;
}

#ifdef __cplusplus
}
#endif

// already included above
// #include "cstddef"
// already included above
// #include "rosidl_runtime_c/message_type_support_struct.h"
// already included above
// #include "interfaces/action/detail/get_status__struct.h"
// already included above
// #include "interfaces/action/detail/get_status__type_support.h"
// already included above
// #include "rosidl_typesupport_c/identifier.h"
// already included above
// #include "rosidl_typesupport_c/message_type_support_dispatch.h"
// already included above
// #include "rosidl_typesupport_c/type_support_map.h"
// already included above
// #include "rosidl_typesupport_c/visibility_control.h"
// already included above
// #include "rosidl_typesupport_interface/macros.h"

namespace interfaces
{

namespace action
{

namespace rosidl_typesupport_c
{

typedef struct _GetStatus_GetResult_Response_type_support_ids_t
{
  const char * typesupport_identifier[2];
} _GetStatus_GetResult_Response_type_support_ids_t;

static const _GetStatus_GetResult_Response_type_support_ids_t _GetStatus_GetResult_Response_message_typesupport_ids = {
  {
    "rosidl_typesupport_fastrtps_c",  // ::rosidl_typesupport_fastrtps_c::typesupport_identifier,
    "rosidl_typesupport_introspection_c",  // ::rosidl_typesupport_introspection_c::typesupport_identifier,
  }
};

typedef struct _GetStatus_GetResult_Response_type_support_symbol_names_t
{
  const char * symbol_name[2];
} _GetStatus_GetResult_Response_type_support_symbol_names_t;

#define STRINGIFY_(s) #s
#define STRINGIFY(s) STRINGIFY_(s)

static const _GetStatus_GetResult_Response_type_support_symbol_names_t _GetStatus_GetResult_Response_message_typesupport_symbol_names = {
  {
    STRINGIFY(ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_fastrtps_c, interfaces, action, GetStatus_GetResult_Response)),
    STRINGIFY(ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_introspection_c, interfaces, action, GetStatus_GetResult_Response)),
  }
};

typedef struct _GetStatus_GetResult_Response_type_support_data_t
{
  void * data[2];
} _GetStatus_GetResult_Response_type_support_data_t;

static _GetStatus_GetResult_Response_type_support_data_t _GetStatus_GetResult_Response_message_typesupport_data = {
  {
    0,  // will store the shared library later
    0,  // will store the shared library later
  }
};

static const type_support_map_t _GetStatus_GetResult_Response_message_typesupport_map = {
  2,
  "interfaces",
  &_GetStatus_GetResult_Response_message_typesupport_ids.typesupport_identifier[0],
  &_GetStatus_GetResult_Response_message_typesupport_symbol_names.symbol_name[0],
  &_GetStatus_GetResult_Response_message_typesupport_data.data[0],
};

static const rosidl_message_type_support_t GetStatus_GetResult_Response_message_type_support_handle = {
  rosidl_typesupport_c__typesupport_identifier,
  reinterpret_cast<const type_support_map_t *>(&_GetStatus_GetResult_Response_message_typesupport_map),
  rosidl_typesupport_c__get_message_typesupport_handle_function,
};

}  // namespace rosidl_typesupport_c

}  // namespace action

}  // namespace interfaces

#ifdef __cplusplus
extern "C"
{
#endif

const rosidl_message_type_support_t *
ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_c, interfaces, action, GetStatus_GetResult_Response)() {
  return &::interfaces::action::rosidl_typesupport_c::GetStatus_GetResult_Response_message_type_support_handle;
}

#ifdef __cplusplus
}
#endif

// already included above
// #include "cstddef"
// already included above
// #include "rosidl_runtime_c/service_type_support_struct.h"
// already included above
// #include "interfaces/action/detail/get_status__type_support.h"
// already included above
// #include "rosidl_typesupport_c/identifier.h"
// already included above
// #include "rosidl_typesupport_c/service_type_support_dispatch.h"
// already included above
// #include "rosidl_typesupport_c/type_support_map.h"
// already included above
// #include "rosidl_typesupport_interface/macros.h"

namespace interfaces
{

namespace action
{

namespace rosidl_typesupport_c
{

typedef struct _GetStatus_GetResult_type_support_ids_t
{
  const char * typesupport_identifier[2];
} _GetStatus_GetResult_type_support_ids_t;

static const _GetStatus_GetResult_type_support_ids_t _GetStatus_GetResult_service_typesupport_ids = {
  {
    "rosidl_typesupport_fastrtps_c",  // ::rosidl_typesupport_fastrtps_c::typesupport_identifier,
    "rosidl_typesupport_introspection_c",  // ::rosidl_typesupport_introspection_c::typesupport_identifier,
  }
};

typedef struct _GetStatus_GetResult_type_support_symbol_names_t
{
  const char * symbol_name[2];
} _GetStatus_GetResult_type_support_symbol_names_t;

#define STRINGIFY_(s) #s
#define STRINGIFY(s) STRINGIFY_(s)

static const _GetStatus_GetResult_type_support_symbol_names_t _GetStatus_GetResult_service_typesupport_symbol_names = {
  {
    STRINGIFY(ROSIDL_TYPESUPPORT_INTERFACE__SERVICE_SYMBOL_NAME(rosidl_typesupport_fastrtps_c, interfaces, action, GetStatus_GetResult)),
    STRINGIFY(ROSIDL_TYPESUPPORT_INTERFACE__SERVICE_SYMBOL_NAME(rosidl_typesupport_introspection_c, interfaces, action, GetStatus_GetResult)),
  }
};

typedef struct _GetStatus_GetResult_type_support_data_t
{
  void * data[2];
} _GetStatus_GetResult_type_support_data_t;

static _GetStatus_GetResult_type_support_data_t _GetStatus_GetResult_service_typesupport_data = {
  {
    0,  // will store the shared library later
    0,  // will store the shared library later
  }
};

static const type_support_map_t _GetStatus_GetResult_service_typesupport_map = {
  2,
  "interfaces",
  &_GetStatus_GetResult_service_typesupport_ids.typesupport_identifier[0],
  &_GetStatus_GetResult_service_typesupport_symbol_names.symbol_name[0],
  &_GetStatus_GetResult_service_typesupport_data.data[0],
};

static const rosidl_service_type_support_t GetStatus_GetResult_service_type_support_handle = {
  rosidl_typesupport_c__typesupport_identifier,
  reinterpret_cast<const type_support_map_t *>(&_GetStatus_GetResult_service_typesupport_map),
  rosidl_typesupport_c__get_service_typesupport_handle_function,
};

}  // namespace rosidl_typesupport_c

}  // namespace action

}  // namespace interfaces

#ifdef __cplusplus
extern "C"
{
#endif

const rosidl_service_type_support_t *
ROSIDL_TYPESUPPORT_INTERFACE__SERVICE_SYMBOL_NAME(rosidl_typesupport_c, interfaces, action, GetStatus_GetResult)() {
  return &::interfaces::action::rosidl_typesupport_c::GetStatus_GetResult_service_type_support_handle;
}

#ifdef __cplusplus
}
#endif

// already included above
// #include "cstddef"
// already included above
// #include "rosidl_runtime_c/message_type_support_struct.h"
// already included above
// #include "interfaces/action/detail/get_status__struct.h"
// already included above
// #include "interfaces/action/detail/get_status__type_support.h"
// already included above
// #include "rosidl_typesupport_c/identifier.h"
// already included above
// #include "rosidl_typesupport_c/message_type_support_dispatch.h"
// already included above
// #include "rosidl_typesupport_c/type_support_map.h"
// already included above
// #include "rosidl_typesupport_c/visibility_control.h"
// already included above
// #include "rosidl_typesupport_interface/macros.h"

namespace interfaces
{

namespace action
{

namespace rosidl_typesupport_c
{

typedef struct _GetStatus_FeedbackMessage_type_support_ids_t
{
  const char * typesupport_identifier[2];
} _GetStatus_FeedbackMessage_type_support_ids_t;

static const _GetStatus_FeedbackMessage_type_support_ids_t _GetStatus_FeedbackMessage_message_typesupport_ids = {
  {
    "rosidl_typesupport_fastrtps_c",  // ::rosidl_typesupport_fastrtps_c::typesupport_identifier,
    "rosidl_typesupport_introspection_c",  // ::rosidl_typesupport_introspection_c::typesupport_identifier,
  }
};

typedef struct _GetStatus_FeedbackMessage_type_support_symbol_names_t
{
  const char * symbol_name[2];
} _GetStatus_FeedbackMessage_type_support_symbol_names_t;

#define STRINGIFY_(s) #s
#define STRINGIFY(s) STRINGIFY_(s)

static const _GetStatus_FeedbackMessage_type_support_symbol_names_t _GetStatus_FeedbackMessage_message_typesupport_symbol_names = {
  {
    STRINGIFY(ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_fastrtps_c, interfaces, action, GetStatus_FeedbackMessage)),
    STRINGIFY(ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_introspection_c, interfaces, action, GetStatus_FeedbackMessage)),
  }
};

typedef struct _GetStatus_FeedbackMessage_type_support_data_t
{
  void * data[2];
} _GetStatus_FeedbackMessage_type_support_data_t;

static _GetStatus_FeedbackMessage_type_support_data_t _GetStatus_FeedbackMessage_message_typesupport_data = {
  {
    0,  // will store the shared library later
    0,  // will store the shared library later
  }
};

static const type_support_map_t _GetStatus_FeedbackMessage_message_typesupport_map = {
  2,
  "interfaces",
  &_GetStatus_FeedbackMessage_message_typesupport_ids.typesupport_identifier[0],
  &_GetStatus_FeedbackMessage_message_typesupport_symbol_names.symbol_name[0],
  &_GetStatus_FeedbackMessage_message_typesupport_data.data[0],
};

static const rosidl_message_type_support_t GetStatus_FeedbackMessage_message_type_support_handle = {
  rosidl_typesupport_c__typesupport_identifier,
  reinterpret_cast<const type_support_map_t *>(&_GetStatus_FeedbackMessage_message_typesupport_map),
  rosidl_typesupport_c__get_message_typesupport_handle_function,
};

}  // namespace rosidl_typesupport_c

}  // namespace action

}  // namespace interfaces

#ifdef __cplusplus
extern "C"
{
#endif

const rosidl_message_type_support_t *
ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_c, interfaces, action, GetStatus_FeedbackMessage)() {
  return &::interfaces::action::rosidl_typesupport_c::GetStatus_FeedbackMessage_message_type_support_handle;
}

#ifdef __cplusplus
}
#endif

#include "action_msgs/msg/goal_status_array.h"
#include "action_msgs/srv/cancel_goal.h"
#include "interfaces/action/get_status.h"
// already included above
// #include "interfaces/action/detail/get_status__type_support.h"

static rosidl_action_type_support_t _interfaces__action__GetStatus__typesupport_c;

#ifdef __cplusplus
extern "C"
{
#endif

const rosidl_action_type_support_t *
ROSIDL_TYPESUPPORT_INTERFACE__ACTION_SYMBOL_NAME(
  rosidl_typesupport_c, interfaces, action, GetStatus)()
{
  // Thread-safe by always writing the same values to the static struct
  _interfaces__action__GetStatus__typesupport_c.goal_service_type_support =
    ROSIDL_TYPESUPPORT_INTERFACE__SERVICE_SYMBOL_NAME(
    rosidl_typesupport_c, interfaces, action, GetStatus_SendGoal)();
  _interfaces__action__GetStatus__typesupport_c.result_service_type_support =
    ROSIDL_TYPESUPPORT_INTERFACE__SERVICE_SYMBOL_NAME(
    rosidl_typesupport_c, interfaces, action, GetStatus_GetResult)();
  _interfaces__action__GetStatus__typesupport_c.cancel_service_type_support =
    ROSIDL_TYPESUPPORT_INTERFACE__SERVICE_SYMBOL_NAME(
    rosidl_typesupport_c, action_msgs, srv, CancelGoal)();
  _interfaces__action__GetStatus__typesupport_c.feedback_message_type_support =
    ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(
    rosidl_typesupport_c, interfaces, action, GetStatus_FeedbackMessage)();
  _interfaces__action__GetStatus__typesupport_c.status_message_type_support =
    ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(
    rosidl_typesupport_c, action_msgs, msg, GoalStatusArray)();

  return &_interfaces__action__GetStatus__typesupport_c;
}

#ifdef __cplusplus
}
#endif
