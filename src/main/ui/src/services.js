import {API_ROOT} from "./api-config";

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
