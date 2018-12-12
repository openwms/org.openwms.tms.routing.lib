import React, {Component} from 'react';
import RouteManagement from "./routes/RouteManagement";
import ActionManagement from "./actions/ActionManagement";

class Start extends Component {

    render() {
        return (
            <div>
                <ActionManagement />
                <RouteManagement />
            </div>
        );
    }
}

export default Start;