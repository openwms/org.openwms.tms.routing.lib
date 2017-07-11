import React from 'react';

import CreateForm from './CreateForm';
import EditForm from './EditForm';
import RouteList from './RouteList';

class App extends React.Component {

    constructor() {
        super()

        this.state = {
            mode: 'list',
            current: { enabled: true },
            routes: []
            /*
                        routes: [
                            { name: "Route 1", description: "Route 1 Desc", sourceLocation: "EXT_/0000/0000/0000/0000", targetLocation: "FG__/0001/0000/0000/0000", sourceLocationGroupName: "", targetLocationGroupName: "FGAISLE1", enabled: true },
                            { name: "Route 2", description: "Route 2 Desc", sourceLocation: "FGIN/0001/0000/0000/0000", targetLocation: "", sourceLocationGroupName: "FGAISLE1", targetLocationGroupName: "ZILE", enabled: true },
                            { name: "Route 3", description: "Route 3 Desc", sourceLocation: "FG__/TIPP/0001/0001/0000", targetLocation: "FG__/TIPP/0002/0001/0000", sourceLocationGroupName: "FGTIPP", targetLocationGroupName: "FGCARTON", enabled: false },
                        ],
            */
        }
        this._findAll()
    }

    handleCreateRoute(route) {
        this.setState({ mode: 'create' })
    }

    handleDeleteRoute(key) {
        console.log('Deleting Route with key ' + key)
        this.setState({ routes: this.state.routes.filter(function (r) { return r.key != key }) })
        this._delete(key)
    }

    handleModifyRoute(routeName) {
        console.log('Modify Route with name ' + routeName)
        const route = this.state.routes.filter(function (r) { return r.name == routeName });
        this.setState({
            current: route[0],
            mode: 'edit'
        })
    }

    handleRouteStatusChange(key, status) {
        this.setState({
            routes: this.state.routes.map(function (r) { if (r.key == key) { r.enabled = status; return r } else { return r } }),
            mode: 'list'
        })
        const route = this.state.routes.filter(function (r) { return r.key == key })
        this._save(route[0])
    }

    handleCancel() {
        this.setState({
            mode: 'list',
            current: {},
        }
        )
    }

    handleCreate(route) {
        this._add(route, (res) => {
            const location = res.headers.get('Location')
            let routeArr = this.state.routes;
            if (res.status === 201 && location) {
                route.key = location.substring(location.lastIndexOf('/')+1, location.length)
                console.log('Route created with key: ' + route.key)
                routeArr = this.state.routes.concat(route)
            }
            this.setState({
                mode: 'list',
                routes: routeArr,
            })
        })
    }

    handleModify(route) {
        const routeArr = (route && route.name) ? this.state.routes.concat(route) : this.state.routes;
        this.setState({
            mode: 'list',
            routes: routeArr,
        })
        this._save(route)
    }

    _findAll() {
        const apiUrl = 'http://tms-routing-infrastructure.app.gcaas.ch/routes'
        fetch(apiUrl, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Authorization': 'Basic ' + btoa("user:sa"),
                'X-Requested-With': 'XMLHttpRequest',
            }
        })
            .then((response) => response.json())
            .then((routes) => {
                this.setState({ routes: routes })
            })
    }

    _add(route, callback) {
        const apiUrl = 'http://tms-routing-infrastructure.app.gcaas.ch/routes'
        fetch(apiUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Basic ' + btoa("user:sa"),
                'X-Requested-With': 'XMLHttpRequest',
            },
            body: JSON.stringify(route)
        })
            .then((response) => callback(response))
    }

    _save(route) {
        const apiUrl = 'http://tms-routing-infrastructure.app.gcaas.ch/routes'
        fetch(apiUrl, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Basic ' + btoa("user:sa"),
                'X-Requested-With': 'XMLHttpRequest',
            },
            body: JSON.stringify(route)
        })
    }

    _delete(key) {
        console.log('Delete ' + key)
        const apiUrl = 'http://tms-routing-infrastructure.app.gcaas.ch/routes/' + key
        fetch(apiUrl, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Authorization': 'Basic ' + btoa("user:sa"),
                'X-Requested-With': 'XMLHttpRequest',
            }
        })        
    }

    render() {
        if (this.state.mode == 'create') {
            return (
                <div className='container'>
                    <div className='row'>
                        <CreateForm
                            onBack={this.handleCancel.bind(this)}
                            onSave={this.handleCreate.bind(this)}
                        />
                    </div>
                </div>
            );
        }
        if (this.state.mode == 'edit') {
            return (
                <div className='container'>
                    <div className='row'>
                        <EditForm value={this.state.current}
                            onBack={this.handleCancel.bind(this)}
                            onSave={this.handleModify.bind(this)}
                        />
                    </div>
                </div>
            );
        }
        else {
            return (
                <div className='container'>
                    <div className='row'>
                        <RouteList routes={this.state.routes}
                            onCreate={this.handleCreateRoute.bind(this)}
                            onDelete={this.handleDeleteRoute.bind(this)}
                            onModify={this.handleModifyRoute.bind(this)}
                            onChangeStatus={this.handleRouteStatusChange.bind(this)}
                        />
                    </div>
                </div>
            );
        }
    }
}

export default App;