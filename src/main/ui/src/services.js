import {API_ROOT} from "./api-config";

function error(message, response) {
    let error = new Error(message);
    if (response.statusText !== '') {
        error.message = error.message + ' ['+response.statusText+']';
    } else {
        if (response.status === 404) {
            error.message = error.message + ' [Not Found]';
        }
        else if (response.status >= 400 && response.status < 500) {
            error.message = error.message + ' [Client Error]';
        }
        else if (response.status >= 500) {
            error.message = error.message + ' [Server Error]';
        }
    }
    error.response = response;
    return error;
}

export function loadLocationGroups(onSuccess, onError) {
    console.log('[Services] Loading LocationGroups');
    const apiUrl = `${API_ROOT}/location-groups`;
    fetch(apiUrl, {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Authorization': 'Basic ' + btoa("user:sa"),
            'X-Requested-With': 'XMLHttpRequest',
        }
    })
    .then(response => response.json())
    .then(data => {
        if (data.status) {
            console.warn(data.message)
        } else {
            onSuccess(data);
        }
    })
    .catch(e => onError(new Error(e.message)));
}

export function loadRoutes(onSuccess, onError) {
    console.log('[Services] Loading Routes');
    const apiUrl = `${API_ROOT}/routes`;
    fetch(apiUrl, {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Authorization': 'Basic ' + btoa("user:sa"),
            'X-Requested-With': 'XMLHttpRequest',
        }
    })
        .then(response => response.json())
        .then(data => {
            if (data.status) {
                console.warn(data.message)
            } else {
                onSuccess(data);
            }
        })
        .catch(e => onError(new Error(e.message)));
}

export function createRoute(route, onSuccess, onError) {
    console.log('[Services] Create a new Route: ' + route);
    const apiUrl = `${API_ROOT}/routes`;
    fetch(apiUrl, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Basic ' + btoa("user:sa"),
            'X-Requested-With': 'XMLHttpRequest',
        },
        body: JSON.stringify(route)
    })
        .then(response => {
            if (response.status >= 200 && response.status < 300) {
                onSuccess(response.headers.get('Location'))
            } else {
                onError(error("Couldn't create Route", response));
            }
        })
        .catch(e => onError(new Error("Couldn't create Route " + e.message)));
}

export function saveRoute(route, onSuccess, onError) {
    console.log('[Services] Save an existing Route: ' + route);
    const apiUrl = `${API_ROOT}/routes`;
    fetch(apiUrl, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Basic ' + btoa("user:sa"),
            'X-Requested-With': 'XMLHttpRequest',
        },
        body: JSON.stringify(route)
    })
        .then(response => response.json())
        .then(data => {
            if (data.status) {
                onError(error("Couldn't save Route", data));
            } else {
                onSuccess(data)
            }
        })
        .catch(e => onError(new Error("Couldn't save Route " + e.message)));
}
export function deleteRoute(route, onSuccess, onError) {
    console.log('[Services] Delete a Route: ' + route);
    const apiUrl = `${API_ROOT}/routes/${route.key}`;
    fetch(apiUrl, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Basic ' + btoa("user:sa"),
            'X-Requested-With': 'XMLHttpRequest',
        },
        body: JSON.stringify(route)
    })
        .then(response => {
            if (response.status >= 200 && response.status < 300) {
                onSuccess()
            } else {
                onError(error("Couldn't delete Route", response));
            }
        })
        .catch(e => onError(new Error("Couldn't delete Route " + e.message)));
}





export function loadActions(onSuccess, onError) {
    console.log('[Services] Loading Actions');
    const apiUrl = `${API_ROOT}/actions`;
    fetch(apiUrl, {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Authorization': 'Basic ' + btoa("user:sa"),
            'X-Requested-With': 'XMLHttpRequest',
        }
    })
        .then(response => response.json())
        .then(data => {
            if (data.status) {
                console.warn(data.message)
            } else {
                onSuccess(data);
            }
        })
        .catch(e => onError(new Error(e.message)));
}

export function createAction(action, onSuccess, onError) {
    console.log('[Services] Create a new Action: ' + action);
    const apiUrl = `${API_ROOT}/actions`;
    fetch(apiUrl, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Basic ' + btoa("user:sa"),
            'X-Requested-With': 'XMLHttpRequest',
        },
        body: JSON.stringify(action)
    })
        .then(response => {
            if (response.status >= 200 && response.status < 300) {
                onSuccess(response.headers.get('Location'))
            } else {
                onError(error("Couldn't create Action", response));
            }
        })
        .catch(e => onError(new Error("Couldn't create Action " + e.message)));
}
export function saveAction(action, onSuccess, onError) {
    console.log('[Services] Save an existing Action: ' + action);
    const apiUrl = `${API_ROOT}/actions`;
    fetch(apiUrl, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Basic ' + btoa("user:sa"),
            'X-Requested-With': 'XMLHttpRequest',
        },
        body: JSON.stringify(action)
    })
        .then(response => response.json())
        .then(data => {
            if (data.status) {
                onError(error("Couldn't save Action", data));
            } else {
                onSuccess(data)
            }
        })
        .catch(e => onError(new Error("Couldn't save Action " + e.message)));
}
export function deleteAction(action, onSuccess, onError) {
    console.log('[Services] Delete a Action: ' + action);
    const apiUrl = `${API_ROOT}/actions/${action.key}`;
    fetch(apiUrl, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Basic ' + btoa("user:sa"),
            'X-Requested-With': 'XMLHttpRequest',
        },
        body: JSON.stringify(action)
    })
        .then(response => {
            if (response.status >= 200 && response.status < 300) {
                onSuccess()
            } else {
                onError(error("Couldn't delete Action", response));
            }
        })
        .catch(e => onError(new Error("Couldn't delete Action " + e.message)));
}
