import React from 'react';
import {connect} from 'react-redux'
import {withStyles} from '@material-ui/core/styles';
import {
    Button,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    FormControl,
    FormGroup,
    MenuItem,
    Select,
    TextField
} from '@material-ui/core'
import {ACTIONS} from '@ameba/ameba-js'
import {saveRoute} from '../../actions/index';
import * as types from '../../constants/ActionTypes';
import FormHelperText from "@material-ui/core/FormHelperText/FormHelperText";
import FormControlLabel from "@material-ui/core/FormControlLabel/FormControlLabel";
import Checkbox from "@material-ui/core/Checkbox/Checkbox";

const styles = theme => ({
    root: {
        display: 'flex',
        flexWrap: 'wrap',
    },
    formControl: {
        margin: theme.spacing.unit,
        minWidth: 300,
    },
    selectEmpty: {
        marginTop: theme.spacing.unit * 2,
    },
});

class RouteDialog extends React.Component {

    _onChange(o) {
        let val = Object.assign({}, this.props.value);
        o(val);
        this.props.onChange(Object.assign({}, val));
    }

    _handleClose = () => this.props.closeModal();

    _handleSubmit = () => this.props.onSubmit(this.props.value);

    render() {
        const { key, name, description, sourceLocationName, sourceLocationGroupName, targetLocationName, targetLocationGroupName, enabled } = this.props.value;
        return (
            <Dialog
                open={this.props.modalOpen}
                onClose={this._handleClose}
                aria-labelledby="form-dialog-title"
            >
                <DialogTitle>{key ? 'Change Route' : 'Create Route'}</DialogTitle>
                <DialogContent>
                    <FormGroup>
                        <FormControl className={this.props.classes.formControl}>
                            <TextField
                                id="name-input"
                                required={true}
                                margin="normal"
                                value={name}
                                onChange={e => this._onChange((val) => val.name = e.target.value)}
                            />
                            <FormHelperText>Name</FormHelperText>
                        </FormControl>
                        <FormControl className={this.props.classes.formControl}>
                            <TextField
                                id="desc-input"
                                margin="normal"
                                value={description}
                                onChange={e => this._onChange((val) => val.description = e.target.value)}
                            />
                            <FormHelperText>Description</FormHelperText>
                        </FormControl>
                        <FormControl className={this.props.classes.formControl}>
                            <TextField
                                id="sourceLocation-input"
                                margin="normal"
                                value={sourceLocationName}
                                onChange={e => this._onChange((val) => val.sourceLocationName = e.target.value)}
                            />
                            <FormHelperText>Source Location</FormHelperText>
                        </FormControl>
                        <FormControl className={this.props.classes.formControl}>
                            <Select
                                id='sourceLocationGroup-input'
                                value={sourceLocationGroupName}
                                onChange={e => this._onChange((val) => val.sourceLocationGroupName = e.target.value)}
                                autoWidth={true}
                            >
                                <MenuItem key={'_NONE_'} value="">None</MenuItem>
                                {this.props.locationGroups.map((v) =>
                                    <MenuItem key={v.name} value={v.name}>{v.name}</MenuItem>)
                                }
                            </Select>
                            <FormHelperText>Source Location Group</FormHelperText>
                        </FormControl>
                        <FormControl className={this.props.classes.formControl}>
                            <TextField
                                id="targetLocation-input"
                                margin="normal"
                                value={targetLocationName}
                                onChange={e => this._onChange((val) => val.targetLocationName = e.target.value)}
                            />
                            <FormHelperText>Target Location</FormHelperText>
                        </FormControl>
                        <FormControl className={this.props.classes.formControl}>
                            <Select
                                id="targetLocationGroup-input"
                                value={targetLocationGroupName}
                                onChange={e => this._onChange((val) => val.targetLocationGroupName = e.target.value)}
                                autoWidth={true}
                            >
                                <MenuItem key={'_NONE_'} value="">None</MenuItem>
                                {this.props.locationGroups.map((v) =>
                                    <MenuItem key={v.name} value={v.name}>{v.name}</MenuItem>)
                                }
                            </Select>
                            <FormHelperText>Target Location Group</FormHelperText>
                        </FormControl>
                        <FormControl className={this.props.classes.formControl}>
                            <FormControlLabel
                                control={
                                    <Checkbox checked={enabled} onChange={e => this._onChange((val) => val.enabled = e.target.checked)} value="enabled" />
                                }
                                label="Activate Route"
                            />
                        </FormControl>
                    </FormGroup>
                </DialogContent>
                <DialogActions>
                    <div>
                        <Button color="primary" onClick={this._handleClose}>Cancel</Button>
                        <Button color="primary" onClick={this._handleSubmit}>OK</Button>
                    </div>,
                </DialogActions>
            </Dialog>
        );
    }
}

const mapStateToProps = (state, props) => (
    {
        value: state.route,
        modalOpen: state.createRouteDialogOpen,
        locationGroups: state.locationGroups,
    }
);

const mapDispatchToProps = (dispatch) => (
    {
        closeModal: () => {
            dispatch({
                type: ACTIONS.RESET_ERROR,
            });
            dispatch({
                type: types.ROUTE_DIALOG_CLOSE,
            })
        },
        onChange: (route) => {
            dispatch({
                type: types.UPDATE_ROUTE,
                route: route,
            })
        },
        onSubmit: (route) => {
            dispatch(
                saveRoute(
                    route,
                    () => {
                        dispatch({
                            type: types.ROUTE_DIALOG_CLOSE,
                        });
                    },
                    (e) => {
                        dispatch({
                            type: ACTIONS.SET_ERROR,
                            error: e,
                        })
                    }
                )
            )
        },
    }
);

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(withStyles(styles)(RouteDialog))