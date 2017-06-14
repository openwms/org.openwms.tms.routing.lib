import React from 'react';

import Header from './Header';
import ToolBar from './ToolBar';
import RouteList from './RouteList';

export default class App extends React.Component {

    render() {
        return <div><Header /><ToolBar /><RouteList /></div>;
    }
}
