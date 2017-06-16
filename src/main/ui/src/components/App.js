import React from 'react';

import EditForm from './EditForm';
import RouteList from './RouteList';

class App extends React.Component {

    constructor() {
        super()

        this.state = {
            editMode: false,
            current: {},
            routes: [
                { name: "Route 1", description: "Route 1 Desc", sourceLocation: "sourceLocation", targetLocation: "targetLocation", sourceLocationGroupName: "sourceLocationGroupName", targetLocationGroupName: "targetLocationGroupName", enabled: true },
                { name: "Route 2", description: "Route 2 Desc", sourceLocation: "sourceLocation", targetLocation: "targetLocation", sourceLocationGroupName: "sourceLocationGroupName", targetLocationGroupName: "targetLocationGroupName", enabled: true },
                { name: "Route 3", description: "Route 3 Desc", sourceLocation: "sourceLocation", targetLocation: "targetLocation", sourceLocationGroupName: "sourceLocationGroupName", targetLocationGroupName: "targetLocationGroupName", enabled: false },
            ],
        }
    }

    handleCreate(route) {
        console.dir('Switch to edit form: ' + route)
        this.setState(
            {
                editMode: true,
            }
        )
    }

    handleCancel() {
        this.setState(
            {
                editMode: false,
                current: {},
            }
        )
    }

    handleSaveNew(route) {
        console.log('Add route: ' + route.name)
        const routeArr = this.state.routes.concat(route);
        this.setState(
            {
                editMode: false,
                routes: routeArr,
            }
        )
    }

    render() {
        return (
            <div className='container'>
                {this.state.editMode ? <div className='row'><EditForm value={this.state.current} onBack={this.handleCancel.bind(this)} onSave={this.handleSaveNew.bind(this)} /></div> : <div className='row'><RouteList routes={this.state.routes} onCreate={this.handleCreate.bind(this)}/></div>}
            </div>
        );
    }
}

export default App;