import React, {Component} from 'react';
import RouteManagement from "./routes/RouteManagement";
import ActionManagement from "./actions/ActionManagement";

class Start extends Component {

    render() {
        return (
            <div>
                <RouteManagement />
                <ActionManagement />
            </div>
        );
    }
}

export default Start;