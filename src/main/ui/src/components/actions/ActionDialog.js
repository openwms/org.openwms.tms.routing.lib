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
import {saveAction} from '../../actions/index';
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

class ActionDialog extends React.Component {

    _onChange(o) {
        let val = Object.assign({}, this.props.value);
        o(val);
        this.props.onChange(Object.assign({}, val));
    }

    _handleClose = () => this.props.closeModal();

    _handleSubmit = () => this.props.onSubmit(this.props.value);

    render() {
        const { key, name, type, description, route, program, location, locationGroupName, enabled } = this.props.value;
        return (
            <Dialog
                open={this.props.modalOpen}
                onClose={this._handleClose}
                aria-labelledby="form-dialog-title"
            >
                <DialogTitle>{key ? 'Change Action' : 'Create Action'}</DialogTitle>
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
                                id="type-input"
                                required={true}
                                margin="normal"
                                value={type}
                                onChange={e => this._onChange((val) => val.type = e.target.value)}
                            />
                            <FormHelperText>Type</FormHelperText>
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
                            <Select
                                id="route-input"
                                value={route}
                                onChange={e => this._onChange((val) => val.route = e.target.value)}
                                autoWidth={true}
                            >
                                {this.props.routes.map((r) =>
                                    <MenuItem key={r.name} value={r.name}>{r.name}</MenuItem>)
                                }
                            </Select>
                            <FormHelperText>Route</FormHelperText>
                        </FormControl>
                        <FormControl className={this.props.classes.formControl}>
                            <TextField
                                id="program-input"
                                margin="normal"
                                value={program}
                                onChange={e => this._onChange((val) => val.program = e.target.value)}
                            />
                            <FormHelperText>Program</FormHelperText>
                        </FormControl>
                        <FormControl className={this.props.classes.formControl}>
                            <TextField
                                id="location-input"
                                margin="normal"
                                value={location}
                                onChange={e => this._onChange((val) => val.location = e.target.value)}
                            />
                            <FormHelperText>Location</FormHelperText>
                        </FormControl>
                        <FormControl className={this.props.classes.formControl}>
                            <Select
                                id="locationGroupName-input"
                                value={locationGroupName}
                                onChange={e => this._onChange((val) => val.locationGroupName = e.target.value)}
                                autoWidth={true}
                            >
                                <MenuItem key={'_NONE_'} value="">None</MenuItem>
                                {this.props.locationGroups.map((v) =>
                                    <MenuItem key={v.name} value={v.name}>{v.name}</MenuItem>)
                                }
                            </Select>
                            <FormHelperText>Location Group</FormHelperText>
                        </FormControl>
                        <FormControl className={this.props.classes.formControl}>
                            <FormControlLabel
                                control={
                                    <Checkbox checked={enabled} onChange={e => this._onChange((val) => val.enabled = e.target.checked)} value="enabled" />
                                }
                                label="Activate Action"
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
        value: state.action,
        modalOpen: state.createActionDialogOpen,
        routes: state.routes,
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
                type: types.ACTION_DIALOG_CLOSE,
            })
        },
        onChange: (action) => {
            dispatch({
                type: types.UPDATE_ACTION,
                action: action,
            })
        },
        onSubmit: (action) => {
            dispatch(
                saveAction(
                    action,
                    () => {
                        dispatch({
                            type: types.ACTION_DIALOG_CLOSE,
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
)(withStyles(styles)(ActionDialog))